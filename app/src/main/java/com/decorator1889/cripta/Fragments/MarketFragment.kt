package com.decorator1889.cripta.Fragments

import DeFiAdapter
import EcomAdapter
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bestseller.coupons.sam.Network.RetrofitApi
import com.decorator1889.cripta.Constant
import com.decorator1889.cripta.Models.MarketsModel
import com.decorator1889.cripta.Models.MarketsModelResponce

import com.decorator1889.cripta.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_market.*
import java.util.*

class MarketFragment : Fragment(), EcomAdapter.Clicker  {

    var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var layoutManager: LinearLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_market, container, false)
        loadCompany()


        val fragmentActivity = activity
        if (fragmentActivity != null)
            fragmentActivity.findViewById<View>(R.id.navigation).visibility = View.VISIBLE

        return view
    }

    private fun loadCompany() {
        mCompositeDisposable?.add(
            RetrofitApi.getRetrofit()!!.getMarketsAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::loadRecyclerView, this::initError)
        )
    }

    private fun initError(error: Throwable){
        println(error)
    }

    private fun loadRecyclerView(companies: MarketsModelResponce){
        loader.visibility = View.GONE
        val adapter = EcomAdapter(companies.data, this)
        layoutManager = LinearLayoutManager(activity)
        rcv?.layoutManager = layoutManager
        rcv?.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                Constant.PRICE = adapter.companies[viewHolder.adapterPosition].priceUsd

                val fragment = TransactionFragment()
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.main_container, fragment).addToBackStack(null)
                transaction.commit()
            }
        })

        itemTouchHelper.attachToRecyclerView(rcv)

    }

    override fun OnClick(company: MarketsModel) {

        val bundle = Bundle()
        bundle.putString("id", company.rank)
        bundle.putString("name", company.name.toLowerCase(Locale.ROOT))
        val fragment: Fragment
        fragment = ChartFragment()
        fragment.arguments = bundle
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment).addToBackStack(null)
        transaction.commit()
    }
}