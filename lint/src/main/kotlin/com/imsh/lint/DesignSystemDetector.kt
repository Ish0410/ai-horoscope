package com.imsh.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression

class DesignSystemDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(
            UCallExpression::class.java,
            UQualifiedReferenceExpression::class.java,
        )
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val preferredName = METHOD_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                val name = node.receiver.asRenderString()
                val preferredName = RECEIVER_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }
        }
    }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "DesignSystem",
            briefDescription = "Design system",
            explanation = "This check highlights calls in code that use Compose Material " +
                "composables instead of equivalents from the Now in Android design system " +
                "module.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                DesignSystemDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        // Unfortunately :lint is a Java module and thus can't depend on the :core-designsystem
        // Android module, so we can't use composable function references (eg. ::Button.name)
        // instead of hardcoded names.
        val METHOD_NAMES = mapOf(
            "MaterialTheme" to "HoroscopeTheme",
            "Button" to "HoroscopeButton",
            "OutlinedButton" to "HoroscopeOutlinedButton",
            "TextButton" to "HoroscopeTextButton",
            "FilterChip" to "HoroscopeFilterChip",
            "ElevatedFilterChip" to "HoroscopeFilterChip",
            "NavigationBar" to "HoroscopeNavigationBar",
            "NavigationBarItem" to "HoroscopeNavigationBarItem",
            "NavigationRail" to "HoroscopeNavigationRail",
            "NavigationRailItem" to "HoroscopeNavigationRailItem",
            "TabRow" to "HoroscopeTabRow",
            "Tab" to "HoroscopeTab",
            "IconToggleButton" to "HoroscopeIconToggleButton",
            "FilledIconToggleButton" to "HoroscopeIconToggleButton",
            "FilledTonalIconToggleButton" to "HoroscopeIconToggleButton",
            "OutlinedIconToggleButton" to "HoroscopeIconToggleButton",
            "CenterAlignedTopAppBar" to "HoroscopeMapTopAppBar",
            "SmallTopAppBar" to "HoroscopeTopAppBar",
            "MediumTopAppBar" to "HoroscopeTopAppBar",
            "LargeTopAppBar" to "HoroscopeTopAppBar",
        )
        val RECEIVER_NAMES = mapOf(
            "Icons" to "HoroscopeIcons",
        )

        fun reportIssue(
            context: JavaContext,
            node: UElement,
            name: String,
            preferredName: String,
        ) {
            context.report(
                ISSUE,
                node,
                context.getLocation(node),
                "Using $name instead of $preferredName",
            )
        }
    }
}
