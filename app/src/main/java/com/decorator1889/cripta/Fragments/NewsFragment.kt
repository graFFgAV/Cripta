package com.decorator1889.cripta.Fragments


import NewsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bestseller.coupons.sam.Network.RetrofitApi
import com.decorator1889.cripta.Models.NewsModel
import com.decorator1889.cripta.Models.NewsModelResponce
import com.decorator1889.cripta.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.loader

class NewsFragment : Fragment(), NewsAdapter.Clicker {


    var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var layoutManager: LinearLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        loadCompany()

        val fragmentActivity = activity
        if (fragmentActivity != null)
            fragmentActivity.findViewById<View>(R.id.navigation).visibility = View.VISIBLE

        return view
    }

    private fun loadCompany() {
        mCompositeDisposable?.add(
            RetrofitApi.getRetrofitNews()!!.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::loadRecyclerView, this::initError)
        )
    }

    fun initError(error: Throwable) {
        println(error)
    }

    fun loadRecyclerView(companies: NewsModelResponce) {
        loader.visibility = View.GONE

        val adapter = NewsAdapter(companies.data, this)
        layoutManager = LinearLayoutManager(activity)
        rcvNews?.layoutManager = layoutManager
        rcvNews?.adapter = adapter
    }


    override fun OnClick(company: NewsModel) {
        val bundle = Bundle()
        bundle.putString("Url", company.url)
        val fragment: Fragment
        fragment = WebViewFragment()
        fragment.arguments = bundle
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment).addToBackStack(null)
        transaction.commit()
        }
}



