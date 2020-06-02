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

package tool.compet.appbundle.binder;

import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import tool.compet.appbundle.binder.annotation.DkBindClick;
import tool.compet.appbundle.binder.annotation.DkBindView;
import tool.compet.core.reflection.DkReflectionFinder;
import tool.compet.core.util.DkLogs;

/**
 * Binds resources into fields, methods of an obj via reflection approach.
 * This uses DkReflectionFinder class to improve reflection performance.
 */
public class DkBinder {
	/**
	 * Init view-field for the target. Only DkBindView annotation is supported.
	 */
	public static void bindViews(Object target, View rootView) {
		List<Field> fields = DkReflectionFinder.getIns()
			.findFields(target.getClass(), DkBindView.class, true, false);

		for (Field field : fields) {
			View childView = rootView.findViewById((field.getAnnotation(DkBindView.class)).value());

			if (childView != null) {
				try {
					field.setAccessible(true);
					field.set(target, childView);
				}
				catch (Exception e) {
					DkLogs.logex(DkBinder.class, e);
					DkLogs.complain(DkBinder.class, "Could not initialize field %s inside class %s",
						field.getName(), target.getClass().getName());
				}
			}
		}
	}

	/**
	 * Binds views-click into methods of target. Only DkBindClick annotation is supported.
	 */
	public static void bindClicks(Object target, View rootView) {
		List<Method> methods = DkReflectionFinder.getIns()
			.findMethods(target.getClass(), DkBindClick.class, true, false);

		for (Method method : methods) {
			View childView = rootView.findViewById((method.getAnnotation(DkBindClick.class)).value());

			if (childView != null) {
				childView.setOnClickListener((v) -> {
					try {
						method.setAccessible(true);
						method.invoke(target);
					}
					catch (Exception e) {
						DkLogs.logex(DkBinder.class, e);
						DkLogs.complain(DkBinder.class, "Could not invoke no-arg method %s inside class %s",
							method.getName(), target.getClass().getName());
					}
				});
			}
		}
	}
}
