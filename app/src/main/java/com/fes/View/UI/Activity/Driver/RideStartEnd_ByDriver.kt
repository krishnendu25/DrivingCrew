package com.fes.View.UI.Activity.Driver

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.fes.Constant.Constants
import com.fes.R
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class RideStartEnd_ByDriver : AppCompatActivity() {
    private var carImagePath: String? = null
    private var meterParh: String? = null
    private var flagImage=0
    var  filescanBankPassBook = File(
        Environment.getExternalStorageDirectory(),
        "BankPassbook_ImagePicker"
    )
    var carImageIV: ImageView? = null
    var meterImageIV: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride__start__form)
        iniView()
    }
    fun iniView(){
        carImageIV   = findViewById(R.id.carImageIV);
        meterImageIV = findViewById(R.id.meterImageIV);
    }

    fun CaptureMeterPicture(view: View) {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .saveDir(filescanBankPassBook!!)
            .start()
        flagImage=145
    }
    fun CaptureCarPicture(view: View) {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .saveDir(filescanBankPassBook!!)
            .start()
        flagImage=165
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode){
                145->{
                    try {
                        val imageUri: Uri = data?.data!!
                        if (imageUri!=null)
                            meterImageIV!!.setImageURI(imageUri)
                        meterParh = Constants.saveImagetoSDcard(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri),this).toString()
                    } catch (e: Exception) {
                    }
                }
                165->{
                    try {
                        val imageUri: Uri = data?.data!!
                        if (imageUri!=null)
                            carImageIV!!.setImageURI(imageUri)
                        carImagePath = Constants.saveImagetoSDcard(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri),this)
                            .toString()
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    fun submitStartDetails(view: View) {}

}