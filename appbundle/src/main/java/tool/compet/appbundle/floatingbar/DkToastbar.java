/*
 * Copyright (c) 2018 DarkCompet. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tool.compet.appbundle.floatingbar;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tool.compet.appbundle.R;
import tool.compet.core.config.DkConfig;
import tool.compet.core.view.DkViews;

/**
 * Differ with Android Toast, this just show a floating text on the layout of current activity.
 * Android Toast has different way to show text, it shows text over display, not app.
 */
public class DkToastbar extends DkFloatingbar {
	public static final int DURATION_SHORT = 1500;
	public static final int DURATION_NORMAL = 2000;
	public static final int DURATION_LONG = 3500;

	private static FloatingbarManager manager;
	private final TextView tvMessage;

	protected DkToastbar(Context context, ViewGroup parent, View bar) {
		super(context, parent, bar);

		duration = DURATION_NORMAL;

		tvMessage = bar.findViewById(R.id.tvMessage);
		DkViews.changeBackgroundColor(bar, "#80000000", 16, DkConfig.device.density);
	}

	public static DkToastbar newIns(ViewGroup parent) {
		parent = findSuitableParent(parent);

		if (parent == null) {
			throw new RuntimeException("No suitable parent found");
		}
		// prepare required params for the constructor
		Context context = parent.getContext();
		View bar = LayoutInflater.from(context).inflate(R.layout.dk_toastbar, parent, false);

		return new DkToastbar(context, parent, bar);
	}

	public static DkToastbar newIns(Activity activity) {
		return newIns(activity.findViewById(android.R.id.content));
	}

	@Override
	protected FloatingbarManager getManager() {
		return manager != null ? manager : (manager = new FloatingbarManager());
	}

	@Override
	protected ValueAnimator prepareInAnimation() {
		int height = bar.getHeight();
		bar.setTranslationY(height);

		ValueAnimator va = new ValueAnimator();
		va.setFloatValues(0.0f, 1.0f);
		va.setDuration(200);
		va.setInterpolator(fastOutSlowIn);

		return va;
	}

	@Override
	protected ValueAnimator prepareOutAnimation() {
		ValueAnimator va = new ValueAnimator();
		va.setFloatValues(1.0f, 0.0f);
		va.setDuration(200);
		va.setInterpolator(fastOutSlowIn);

		return va;
	}

	@Override
	protected void onAnimationUpdate(ValueAnimator animation) {
		float alpha = (float) animation.getAnimatedValue();
		bar.setAlpha(alpha);
	}

	public DkToastbar setMessage(int msgRes) {
		tvMessage.setText(msgRes);
		return this;
	}

	public DkToastbar setMessage(CharSequence msg) {
		tvMessage.setText(msg);
		return this;
	}

	public DkToastbar setDuration(long duration) {
		this.duration = duration;
		return this;
	}

	public DkToastbar setOnShownCallback(Runnable onShownCallback) {
		this.onShownCallback = onShownCallback;
		return this;
	}

	public DkToastbar setOnDismissCallback(Runnable onDismissCallback) {
		this.onDismissCallback = onDismissCallback;
		return this;
	}
}
