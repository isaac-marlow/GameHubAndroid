package com.ilhomsoliev.gamehubandroid.app

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader

object FlipperInitializer {
  fun init(application: Application) {
    if (!FlipperUtils.shouldEnableFlipper(application)) return

    SoLoader.init(application, false)
    val client = AndroidFlipperClient.getInstance(application)
    client.addPlugin(InspectorFlipperPlugin(application, DescriptorMapping.withDefaults()))
    client.addPlugin(NetworkFlipperPlugin())
    client.start()
  }
}
