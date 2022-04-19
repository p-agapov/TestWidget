package com.agapovp.android.testwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity

const val TAG: String = "TestAppWidgetConfigActivity"

const val SHARED_PREFS: String = "${TAG}_SHARED_PREFS"
const val KEY_BUTTON_TEXT: String = "${TAG}_KEY_BUTTON_TEXT"

class TestAppWidgetConfigActivity : AppCompatActivity() {

    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_app_widget_config)

        appWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )

        setResult(RESULT_CANCELED, Intent().apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        })

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) finish()

        editText = findViewById(R.id.config_edit_text)

        findViewById<Button>(R.id.config_button).setOnClickListener {
            val pendingIntent =
                PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0)

            val editTextText = editText.text.toString()

            val remoteViews = RemoteViews(this.packageName, R.layout.widget_test).apply {
                setOnClickPendingIntent(R.id.widget_button, pendingIntent)
                setCharSequence(R.id.widget_button, "setText", editTextText)
            }

            AppWidgetManager.getInstance(this).updateAppWidget(appWidgetId, remoteViews)
            getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).apply {
                edit().putString(KEY_BUTTON_TEXT + appWidgetId, editTextText).apply()
            }

            setResult(RESULT_OK, Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            })

            finish()
        }
    }
}
