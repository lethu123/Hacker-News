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

package tool.compet.appbundle.arch.vml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import tool.compet.appbundle.arch.DkSimpleFragment;
import tool.compet.appbundle.eventbus.DkEventBus;

/**
 * View component of VML design pattern.
 */
public abstract class DkVmlFragment extends DkSimpleFragment implements VmlView {
   private List<DkVmlViewLogic> vls;

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // this must be run after #super.onCreate()
      vls = new VmlInjector(this).start();

      for (DkVmlViewLogic vl : vls) {
         vl.onCreate(host, savedInstanceState);
      }
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      for (DkVmlViewLogic vl : vls) {
         vl.onViewCreated(host, savedInstanceState);
      }
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      for (DkVmlViewLogic vl : vls) {
         vl.onActivityCreated(host, savedInstanceState);
      }
   }

   @Override
   public void onStart() {
      super.onStart();

      for (DkVmlViewLogic vl : vls) {
         vl.onStart(host);

         DkEventBus.getIns().register(vl);
      }
   }

   @Override
   public void onActive(boolean isResume) {
      super.onActive(isResume);

      for (DkVmlViewLogic vl : vls) {
         vl.onActive(host, isResume);
      }
   }

   @Override
   public void onInactive(boolean isPause) {
      super.onInactive(isPause);

      for (DkVmlViewLogic vl : vls) {
         vl.onInactive(host, isPause);
      }
   }

   @Override
   public void onStop() {
      for (DkVmlViewLogic vl : vls) {
         vl.onStop(host);

         DkEventBus.getIns().unregister(vl);
      }
      super.onStop();
   }

   @Override
   public void onDestroy() {
      for (DkVmlViewLogic vl : vls) {
         vl.onDestroy(host);
      }
      super.onDestroy();
   }

   @Override
   public void onDetach() {
      if (vls != null) {
         vls.clear();
      }
      super.onDetach();
   }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
      for (DkVmlViewLogic vl : vls) {
         vl.onActivityResult(host, requestCode, resultCode, data);
      }
      super.onActivityResult(requestCode, resultCode, data);
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      for (DkVmlViewLogic vl : vls) {
         vl.onRequestPermissionsResult(host, requestCode, permissions, grantResults);
      }
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
   }

   @Override
   public void onLowMemory() {
      for (DkVmlViewLogic vl : vls) {
         vl.onLowMemory(host);
      }
      super.onLowMemory();
   }

   @Override
   public void onSaveInstanceState(@NonNull Bundle outState) {
      for (DkVmlViewLogic vl : vls) {
         vl.onSaveInstanceState(host, outState);
      }
      super.onSaveInstanceState(outState);
   }
}
