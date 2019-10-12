package com.movefastimagegallery_

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.movefastimagegallery_.API.ApiRequests
import com.movefastimagegallery_.API.JsonParser
import com.movefastimagegallery_.CustomAdatapter.PhotoListAdapter
import com.movefastimagegallery_.Helper.Utils
import com.movefastimagegallery_.Model.ImagesUrl_Model
import com.movefastimagegallery_.listeners.ListItemClickListener
import com.movefastimagegallery_.listeners.ResponseListener
import java.util.*

class MainActivity : AppCompatActivity(), ResponseListener, ListItemClickListener {


    private var loadMore = false

    private lateinit var mImage_RecyclerView: RecyclerView
    private lateinit var nestedSV: NestedScrollView

    private var loadBar: ProgressBar? = null
    var mLayout_recyclerView: GridLayoutManager? = null

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    internal var swithlayout: ImageView? = null

    var pageno = 1
    private var isvertical = true

    var mUtils: Utils? = null
    internal var mImagesUrl_ModelList: MutableList<ImagesUrl_Model> = ArrayList<ImagesUrl_Model>()

    // custom Photo Adapter
    private var mPhotoListAdapter: PhotoListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mUtils = Utils(this@MainActivity)
        //initialize views
        iniView()
        ///calling the funcation for API
        loadMoreData(1, "firsttime")

    }

    fun iniView() {

        mImage_RecyclerView = findViewById(R.id.rv_image)
        nestedSV = findViewById(R.id.scrollView)
        loadBar = findViewById(R.id.loadBar)
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout)
        swithlayout = findViewById(R.id.swithlayout)

        mLayout_recyclerView = GridLayoutManager(this@MainActivity, 2)
        mImage_RecyclerView.setHasFixedSize(true)
        mImage_RecyclerView.isNestedScrollingEnabled = false
        mImage_RecyclerView.setItemViewCacheSize(10)
        mImage_RecyclerView.layoutManager = mLayout_recyclerView

        /*
            pull to refrsh the  call to server and realod data from server.
         */
        mSwipeRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            loadBar!!.visibility = View.VISIBLE
            /// call api for more images
            loadMoreData(pageno, "load_more")

            mSwipeRefreshLayout!!.isRefreshing = false


        })

        /*
        check nestted scrollvew reached bottom of recycleview or not
         */

        nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            /// check NestedScrollView reached end  position or not if its end position load more images and  show loader
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (!loadMore) {

                    loadBar!!.visibility = View.VISIBLE
                    loadMore = true
                    pageno = pageno + 1

                    /// call api for more images
                    loadMoreData(pageno, "load_more")

                }
            }
        })
    }

    private fun loadMoreData(page: Int, str_msg: String) {

        //check internet connection is available ot not if not than show the toast message to  user
        if (mUtils!!.isWifi(this)) {

            ///initialize  and call to API using retrofit .
            ApiRequests(this@MainActivity, this).getApiRequestMethodarray(page, str_msg)

        } else {
            loadBar!!.visibility = View.GONE
            mUtils!!.showToast(resources.getString(R.string.nointernet))
        }
    }


    /*
   its ResponseListener interface method
   its have two methods these are
   1= onSuccess
   2=onError
    */
    override fun onSuccess(`object`: String, action: String) {

        loadMore = false
        mImagesUrl_ModelList.addAll(JsonParser.json2ImageList(`object`))

        if (mImagesUrl_ModelList.size > 0) {


            mPhotoListAdapter = PhotoListAdapter(this@MainActivity, mImagesUrl_ModelList, this)
            mImage_RecyclerView.adapter = mPhotoListAdapter

            loadMore = false

        } else {
            loadBar!!.visibility = View.GONE
            loadMore = true
        }

        loadBar!!.visibility = View.GONE
    }

    override fun onError(message: String) {
        loadMore = false
        mUtils!!.showToast(message)
        loadBar!!.visibility = View.GONE

    }

    override fun onListItemClick(position: Int, action: String) {

    }

    /*
    click listener  for change RecyclerView view Orientation
     */
    fun OnSwitchclick(view: View) {

        /*
         check RecyclerView view Orientation if its  gridview than  change
         it in listview
         */
        if (isvertical) {
            swithlayout!!.setImageResource(R.drawable.ic_view_list_black)
            isvertical = false
            switchList(true)
        } else {
            swithlayout!!.setImageResource(R.drawable.ic_grid_on_black)
            switchList(false)
            isvertical = true
        }

    }

    /*
    this funcatin  change the  RecyclerView  Orientation (ListView or Grid gridview)
     */
    fun switchList(isVertical: Boolean) {
        if (isVertical) {
            val specManager = LinearLayoutManager(this)
            specManager.orientation = LinearLayoutManager.VERTICAL

            mImage_RecyclerView.layoutManager = specManager
            mImage_RecyclerView.adapter = mPhotoListAdapter
        } else {
            val mGridLayoutManager = GridLayoutManager(this@MainActivity, 2)

            mImage_RecyclerView.layoutManager = mGridLayoutManager
            mImage_RecyclerView.adapter = mPhotoListAdapter
        }
    }


}
