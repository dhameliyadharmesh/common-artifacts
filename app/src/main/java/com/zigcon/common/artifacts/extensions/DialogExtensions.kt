package com.zigcon.common.artifacts.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.zigcon.common.artifacts.R


fun showLongSnackbar(
    view: View, mesStrId: Int, actionStrId: Int? = null,
    onActionClick: () -> Unit = { -> }
) {
    val snackBar = Snackbar.make(
        view,
        mesStrId,
        Snackbar.LENGTH_LONG
    )
    actionStrId?.apply {
        snackBar.setAction(this) {
            onActionClick()
        }
    }
    snackBar.show()
}

fun showLongSnackBarStoryDownload(
    view: View, mesStrId: Int, actionStrId: Int? = null,
    onActionClick: () -> Unit = { -> }
) {
    val snackBar = Snackbar.make(
        view,
        mesStrId,
        Snackbar.LENGTH_LONG
    )
    snackBar.view.layoutParams = (snackBar.view.layoutParams as FrameLayout.LayoutParams).apply {
        gravity = Gravity.BOTTOM
    }
    actionStrId?.apply {
        snackBar.setAction(this) {
            onActionClick()
        }
    }
    snackBar.show()
}

fun Context.showPermissionSettingAlertDialog(gotoSettings: () -> Unit = { -> }) {
    showAlertDialog(this,
        titleResId = R.string.we_need_permission,
        messageResId = R.string.setting_permission_message,
        positiveButtonResId = R.string.settings,
        negativeButtonResId = R.string.cancel,
        onPositiveClick = { dialogInterface ->
            run {
                dialogInterface.dismiss()
                gotoSettings()
            }
        })
}

fun Context.showPermissionSettingAlertDialogForSaveStories(gotoSettings: () -> Unit = { -> }) {
    showAlertDialog(this,
        titleResId = R.string.we_need_permission,
        messageResId = R.string.write_permission_message,
        positiveButtonResId = R.string.settings,
        cancelable = false,
        onPositiveClick = { dialogInterface ->
            run {
                dialogInterface.dismiss()
                gotoSettings()
            }
        })
}

fun Context.showAppExitAlertDialog(onExit: () -> Unit = { -> }, onRateNow: () -> Unit = { -> }) {
    showAlertDialog(this, R.string.exit_dialog_title, R.string.exit_dialog_message,
        positiveButtonResId = R.string.exit_dialog_continue,
        negativeButtonResId = R.string.exit_dialog_yes,
        neutralButtonResId = R.string.exit_dialog_rate_now,
        cancelable = false,
        onPositiveClick = { dialogInterface -> dialogInterface.dismiss() },
        onNegativeClick = { dialogInterface ->
            run {
                dialogInterface.dismiss()
                onExit()
            }
        }, onNeutralClick = { dialogInterface ->
            run {
                dialogInterface.dismiss()
                onRateNow()
            }
        })
}

fun showAlertDialog(
    context: Context,
    titleResId: Int = 0,
    messageResId: Int = 0,
    positiveButtonResId: Int? = null,
    negativeButtonResId: Int? = null,
    neutralButtonResId: Int? = null,
    cancelable: Boolean = true,
    onPositiveClick: (DialogInterface) -> Unit = { _ -> },
    onNegativeClick: (DialogInterface) -> Unit = { _ -> },
    onNeutralClick: (DialogInterface) -> Unit = { _ -> },
    onListenerBack : (DialogInterface) -> Unit = { _ -> }
    ) {
    val alertBuilder = AlertDialog.Builder(context)
    alertBuilder.setTitle(if (titleResId == 0) "" else context.getString(titleResId))
    alertBuilder.setMessage(if (messageResId == 0) "" else context.getString(messageResId))
    alertBuilder.setCancelable(cancelable)
    positiveButtonResId?.also {
        alertBuilder.setPositiveButton(
            it
        ) { p1, _ ->
            p1.dismiss()
            onPositiveClick(p1)
        }
    }
    negativeButtonResId?.also {
        alertBuilder.setNegativeButton(
            it
        ) { p1, _ ->
            p1.dismiss()
            onNegativeClick(p1)
        }
    }

    neutralButtonResId?.also {
        alertBuilder.setNeutralButton(
            it
        ) { p1, _ ->
            p1.dismiss()
            onNeutralClick(p1)
        }
    }
    val alertDialog = alertBuilder.create()
    onListenerBack(alertDialog)
    alertDialog.show()
}

fun Context.showDeleteAlertDialog(onDelete: () -> Unit = { -> }) {
    showAlertDialog(this,
        titleResId = R.string.delete,
        messageResId = R.string.delete_message,
        positiveButtonResId = R.string.delete,
        negativeButtonResId = R.string.cancel,
        onPositiveClick = { dialogInterface ->
            run {
                dialogInterface.dismiss()
                onDelete()
            }
        })
}



