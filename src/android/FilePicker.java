package com.example.filepicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

public class FilePicker extends CordovaPlugin {

    private static final int PICK_FILE_REQUEST = 1;
    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("pickFile")) {
            this.callbackContext = callbackContext;
            openFilePicker();
            return true;
        }
        return false;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        String[] mimeTypes = {"image/*", "video/*", "application/pdf", "text/plain"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        cordova.startActivityForResult(this, intent, PICK_FILE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                this.callbackContext.success(uri.toString());
            } else {
                this.callbackContext.error("File selection failed.");
            }
        } else if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_CANCELED) {
            this.callbackContext.error("File selection was canceled.");
        }
    }
}
