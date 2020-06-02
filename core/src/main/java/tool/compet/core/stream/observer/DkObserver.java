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

package tool.compet.core.stream.observer;

/**
 * Observable.subscribe()で登録されたObserverをどんどん上のObservableに伝搬していきます。
 * 上の親Observerに何かあったとき、子Observerに通知します。
 */
public interface DkObserver<T> {
	/**
	 * タスクが実行用スレッド(通常はバックグラウンドスレッド)に移る直前に、実行支配可能のControllableが
	 * この関数を通じて子Observerに渡します。子Observerでは、いつでもタスクを支配(キャンセル、再生、停止等)できます。
	 * <p></p>
	 * 注意：この関数は実行に至るまで何回も呼ばれ、ストリームの制御のチャンスをユーザーにあげます。
	 */
	void onSubscribe(DkControllable controllable);

	/**
	 * 新しいアイテムが来たとき、親Observerがこの関数を通じて、子Observerに通知します。
	 * <p></p>
	 * 注意：この関数はアイテム数によって、何回もコールされることがあります。
	 */
	void onNext(T item);

	/**
	 * エラー等が発生したとき、親Observerがこの関数を通じて、子Observerに通知します。
	 * <p></p>
	 * 注意：この関数は最大一回だけコールされます。また、呼ばれるとonComplete()が呼ばれなくなります。
	 */
	void onError(Throwable e);

	/**
	 * キャンセル命令、エラー等がなく、全てのアイテムが正常に処理できたら、この関数が呼び出されます。
	 * <p></p>
	 * 注意：#onComplete() か #onError() のどちらかが呼び出されます。
	 */
	void onComplete();

	/**
	 * エラー等の有無に関わらず、全てのアイテムが処理できたら、この関数が呼び出されます。
	 * <p></p>
	 * 注意：この関数は必ず一回だけ呼び出されます。
	 */
	void onFinal();
}
