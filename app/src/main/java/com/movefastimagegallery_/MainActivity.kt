package com.movefastimagegallery_

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private var loadMore = false

    private lateinit var mImage_RecyclerView: RecyclerView
    private lateinit var nestedSV: NestedScrollView

    private var loadBar: ProgressBar? = null
    var mLayout_recyclerView: GridLayoutManager? = null

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    internal var swithlayout: ImageView? = null

    var pageno = 1
    private var isvertical = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialize views
        iniView()

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
            mSwipeRefreshLayout!!.isRefreshing = false
            /// call api for more images
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


                }
            }
        })
    }

}
