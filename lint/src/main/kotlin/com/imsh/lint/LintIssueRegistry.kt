package com.imsh.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

/**
 * Custom Lint Class
 */
class LintIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        DesignSystemDetector.ISSUE
    )

    override val api = CURRENT_API

    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Gallery Maps",
        feedbackUrl = "https://github.com/Ish0410/gallery-map/issues",
        contact = "https://github.com/Ish0410/gallery-map",
    )
}