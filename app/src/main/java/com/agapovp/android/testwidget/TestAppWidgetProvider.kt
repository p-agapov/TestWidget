package com.agapovp.android.testwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class TestAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach { appWidgetId ->
            val pendingIntent = PendingIntent.getActivity(
                context, 0, Intent(context, MainActivity::class.java), 0
            )
            val remoteViews = RemoteViews(context?.packageName, R.layout.widget_test).apply {
                setOnClickPendingIntent(R.id.widget_button, pendingIntent)
            }
            appWidgetManager?.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}
