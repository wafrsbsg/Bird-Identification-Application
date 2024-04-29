package com.example.fypbird

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fypbird.databinding.ActivityIdentificationBinding
import com.example.fypbird.ml.MmodelMobilenetModelV2
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class IdentificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentificationBinding
    private lateinit var iv_photo: ImageView
    private lateinit var btn_photo: Button
    private lateinit var tv_output: TextView
    private lateinit var btn_upload: Button
    private lateinit var btn_guide: Button
    private lateinit var sqliteHelper: SQLLiteHelper

    //image processor
    var imageProcess = ImageProcessor.Builder()
        .add(ResizeOp(224,224,ResizeOp.ResizeMethod.BILINEAR))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        iv_photo = binding.ivPhoto
        btn_photo = binding.btnPhoto
        tv_output = binding.tvOutput

        sqliteHelper = SQLLiteHelper(this)

        //check permission
        btn_photo.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                takePicturePreview.launch(null)
            } else {
                requestPermission.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    fun goToHome(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //request camera permission
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePicturePreview.launch(null)
            } else {
                Toast.makeText(this, "Permission Denied !! Try again", Toast.LENGTH_SHORT).show()
            }
        }

    //launch camera and take picture
    private val takePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                iv_photo.setImageBitmap(bitmap)
                outputGenerator(bitmap)
            }
        }


    private fun outputGenerator(bitmap: Bitmap) {


        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)

        tensorImage = imageProcess.process(tensorImage)

        val model = MmodelMobilenetModelV2.newInstance(this)

        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        //var confidences = outputFeature0.floatArray

        var maxIdx = 0
        outputFeature0.forEachIndexed{ index, fl ->
            if (outputFeature0[maxIdx] < fl){
                maxIdx = index
            }
        }

        //var maxPos = 0
        //var maxConfidence = 0f
        //for (i in confidences.indices) {
        //    if (confidences[i] > maxConfidence) {
        //        maxConfidence = confidences[i]
        //        maxPos = i
        //    }
       //}

        //var max = outputFeature0.floatArray[0]

        val classes = arrayOf("ABBOTTS BABBLER", "ABBOTTS BOOBY", "ABYSSINIAN GROUND HORNBILL", "AFRICAN CROWNED CRANE", "AFRICAN EMERALD CUCKOO")

        //classes[maxPos]
        tv_output.setText(classes[maxIdx])
            //"0:" + outputFeature0.floatArray[0].toString() +
              //          "1:" + outputFeature0.floatArray[1].toString() +
                //"2:" + outputFeature0.floatArray[2].toString() +
                //"3:" + outputFeature0.floatArray[3].toString() +
                //"4:" + outputFeature0.floatArray[4].toString()

        // Releases model resources if no longer used.
        model.close()

        //change photo to bitmap, save bitmap and pic to database
        val uploadButton: Button = findViewById(R.id.btn_upload)
        uploadButton.setOnClickListener{
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            val b = baos.toByteArray()
            val base64 = Base64.encodeToString(b, Base64.DEFAULT )
            if (classes[maxIdx].isEmpty()){
                Toast.makeText(this, "please take picture", Toast.LENGTH_SHORT).show()
            }else{
                val pic = PicModel(bitmap = base64.toString(), birdname = classes[maxIdx])
                val status = sqliteHelper.insertPic(pic)

                if (status > -1){
                    Toast.makeText(this, "Picture Added", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //send birname to Bird Guide Function and show bird information
        val guideButton: Button = findViewById(R.id.btn_guide)
        guideButton.setOnClickListener{
            if (classes[maxIdx].isEmpty()){
                Toast.makeText(this, "please take picture", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GuideDetailActivity::class.java)
                intent.putExtra("birdname", classes[maxIdx])
                intent.putExtra("from", "ID")
                startActivity(intent)
            }
        }


    }
}