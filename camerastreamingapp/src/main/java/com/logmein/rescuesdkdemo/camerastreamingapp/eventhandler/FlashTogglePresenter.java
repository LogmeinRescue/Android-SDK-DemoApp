package com.logmein.rescuesdkdemo.camerastreamingapp.eventhandler;

import android.view.View;

import com.logmein.rescuesdk.api.eventbus.Subscribe;
import com.logmein.rescuesdk.api.ext.CameraStreamingExtension;
import com.logmein.rescuesdk.api.remoteview.camera.event.FlashlightAvailableEvent;
import com.logmein.rescuesdk.api.remoteview.camera.event.FlashlightTurnedOffEvent;
import com.logmein.rescuesdk.api.remoteview.camera.event.FlashlightTurnedOnEvent;
import com.logmein.rescuesdk.api.remoteview.camera.event.FlashlightUnavailableEvent;

/**
 * Manipulates the flashlight toggle button based on the related events.
 */
public class FlashTogglePresenter {

    private final View toggleButton;
    private final CameraStreamingExtension extension;

    private final View.OnClickListener flashOnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            extension.flashOn();
        }
    };

    private final View.OnClickListener flashOffListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            extension.flashOff();
        }
    };

    public FlashTogglePresenter(View toggleButton, CameraStreamingExtension extension) {
        this.toggleButton = toggleButton;
        this.extension = extension;
    }

    @Subscribe
    public void onFlashAvailable(FlashlightAvailableEvent event) {
        showButton();
    }

    @Subscribe
    public void onFlashUnavailable(FlashlightUnavailableEvent event) {
        hideButton();
    }

    @Subscribe
    public void onFlashTurnedOn(FlashlightTurnedOnEvent event) {
        toggleButton.setOnClickListener(flashOffListener);
    }

    @Subscribe
    public void onFlashTurnedOff(FlashlightTurnedOffEvent event) {
        toggleButton.setOnClickListener(flashOnListener);
    }

    private void showButton() {
        toggleButton.setVisibility(View.VISIBLE);
        toggleButton.setOnClickListener(flashOnListener);
    }

    private void hideButton() {
        toggleButton.setVisibility(View.GONE);
        toggleButton.setOnClickListener(null);
    }
}
