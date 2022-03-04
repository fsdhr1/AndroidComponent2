package com.gykj.commontool.addressselect;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gykj.addressselect.AddressWidgetUtils;
import com.gykj.addressselect.AddressWidget;
import com.gykj.addressselect.bean.AddreBean;
import com.gykj.addressselect.interfaces.OnAddressSelectedListener;
import com.gykj.commontool.R;

import static com.gykj.addressselect.AddressViewsController.INDEX_TAB_CITY;
import static com.gykj.addressselect.AddressViewsController.INDEX_TAB_COUNTY;
import static com.gykj.addressselect.AddressViewsController.INDEX_TAB_PROVINCE;
import static com.gykj.addressselect.AddressViewsController.INDEX_TAB_TOWN;
import static com.gykj.addressselect.AddressViewsController.INDEX_TAB_VILLAGE;

public class AddressSelectActivity extends AppCompatActivity implements OnAddressSelectedListener {

    AddressWidget mAddressWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select);
        // 显示
        Button mButton = findViewById(R.id.btn_address);
        mButton.setOnClickListener(v -> {
            mAddressWidget = AddressWidgetUtils.getInstance().getWidgetCache(AddressSelectActivity.this);
            mAddressWidget.setOnAddressSelectedListener(AddressSelectActivity.this);
            mAddressWidget.showWidget();
//            mAddressWidget.showWidget("21");
//            mAddressWidget.onDissWidget();
        });

        // 手动输入区划代码
        Button mBtqhdm = findViewById(R.id.btn_input_address);
        EditText mEditText = new EditText(this);
        AlertDialog mDialog = createAlertDialog(mEditText);
        mBtqhdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (mDialog != null) {
                    mDialog.show();
                }
            }
        });

    }

    private AlertDialog createAlertDialog(EditText mEditText) {
        
        AlertDialog mDialog = new AlertDialog.Builder(this).setTitle("请输入区划代码")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(mEditText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mInterface, int i) {
                        String input = mEditText.getText().toString().trim();
                        if (TextUtils.isEmpty(input)) return;
//                        mBottomAddressDialog.showDialog(input);
                        AddressWidgetUtils.getInstance().getWidgetNew(AddressSelectActivity.this)
                                .setOnAddressSelectedListener(AddressSelectActivity.this::onAddressSelected)
                                .showWidget(input);
                        mInterface.dismiss();
                    }
                }).setNegativeButton("取消", null).create();
        return mDialog;
    }


    /**
     * 地址选择器选中的回调
     *
     * @param index
     * @param province
     * @param city
     * @param county
     * @param town
     * @param village
     */
    @Override
    public void onAddressSelected(int index, AddreBean province, AddreBean city, AddreBean county, AddreBean town, AddreBean village) {
        switch (index) {
            case INDEX_TAB_PROVINCE: //
                // 根据省份 查询 城市
                mAddressWidget.getController().retrieveCitiesWith(province.getQhdm());
                break;
            case INDEX_TAB_CITY://
                // 根据城市 查询 县
                mAddressWidget.getController().retrieveCountiesWith(city.getQhdm());
                break;
            case INDEX_TAB_COUNTY://
                // 根据县找到乡
                mAddressWidget.getController().retrieveTownWith(county.getQhdm());
                break;
            case INDEX_TAB_TOWN:
                // 根据乡找到村
                mAddressWidget.getController().retrieveVillage(town.getQhdm());
                break;
            case INDEX_TAB_VILLAGE://
                String liveAddress = "";
                if (province != null)
                    liveAddress += province.getQhmc();
                if (city != null)
                    liveAddress += city.getQhmc();
                if (county != null)
                    liveAddress += county.getQhmc();
                if (town != null)
                    liveAddress += town.getQhmc();
                if (village != null)
                    liveAddress += village.getQhmc();

                Toast.makeText(this, liveAddress, Toast.LENGTH_SHORT).show();
                //  mBottomAddressDialog.dissmissDialog();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        mAddressWidget.onWidgetDestory();
//        Log.e("mAddressWidget", "----- mAddressWidgetonDestroy -----");
    }
}