package com.blend.jetpackstudy.viewbinding

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding是一种用于替代findViewById的方式，它可以通过编译时生成的代码来绑定视图，避免了类型转换和空指针异常。
 *
 * 从生成的ActivityMainBinding文件中可以轻松地看出，在调用了inflate之后会调用bind方法，而bind方法依然是通过findViewById绑定的，
 * getRoot方法返回的即为根布局的View，在这里则是LinearLayout。不管采用哪种实现方式，最终都会转化为由findViewById函数实现.
 *
 * 下面是ViewBinding生成的代码示例：
 *
 * public final class ActivityMainBinding implements ViewBinding {
 *     @NonNull
 *     private final LinearLayout rootView;
 *     @NonNull
 *     public final Button btnConfirm;
 *     @NonNull
 *     public final EditText edContent;
 *     @NonNull
 *     public final TextView tvContent;
 *     private ActivityMainBinding(@NonNull LinearLayout rootView, @NonNull Button btnConfirm, @NonNull EditText edContent, @NonNull TextView tvContent) {
 *         this.rootView = rootView;
 *         this.btnConfirm = btnConfirm;
 *         this.edContent = edContent;
 *         this.tvContent = tvContent;
 *     }
 *     @NonNull
 *     public LinearLayout getRoot() {
 *         return this.rootView;
 *     }
 *     @NonNull
 *     public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
 *         return inflate(inflater, (ViewGroup)null, false);
 *     }
 *     @NonNull
 *     public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, boolean attachToParent) {
 *         View root = inflater.inflate(2131427356, parent, false);
 *         if (attachToParent) {
 *             parent.addView(root);
 *         }
 *         return bind(root);
 *     }
 *     @NonNull
 *     public static ActivityMainBinding bind(@NonNull View rootView) {
 *         int id = 2131230808;
 *         Button btnConfirm = (Button)rootView.findViewById(id);
 *         if (btnConfirm ！= null) {
 *             id = 2131230865;
 *             EditText edContent = (EditText)rootView.findViewById(id);
 *             if (edContent ！= null) {
 *                 id = 2131231085;
 *                 TextView tvContent = (TextView)rootView.findViewById(id);
 *                 if (tvContent ！= null) {
 *                     return new ActivityMainBinding((LinearLayout)rootView, btnConfirm, edContent, tvContent);
 *                 }
 *             }
 *         }
 *         String missingId = rootView.getResources().getResourceName(id);
 *         throw new NullPointerException("Missing required view with ID: ".concat(missingId));
 *     }
 * }
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    lateinit var mViewBinding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val cls = type.actualTypeArguments[0] as Class<*>
            cls.getDeclaredMethod("inflate", LayoutInflater::class.java).let {
                @Suppress("UNCHECKED_CAST")
                mViewBinding = it.invoke(null, layoutInflater) as T
                setContentView(mViewBinding.root)
            }
        }
    }
}