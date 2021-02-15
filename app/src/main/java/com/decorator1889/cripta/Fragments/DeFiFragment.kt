package com.decorator1889.cripta.Fragments

import DeFiAdapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bestseller.coupons.sam.Network.RetrofitApi
import com.decorator1889.cripta.Constant
import com.decorator1889.cripta.Models.DeFIModelResponce
import com.decorator1889.cripta.Models.DeFiModel
import com.decorator1889.cripta.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.defi_fragment.*
import kotlinx.android.synthetic.main.fragment_market.*
import kotlinx.android.synthetic.main.fragment_market.loader
import java.util.*

class DeFiFragment : Fragment(), DeFiAdapter.Clicker  {

    var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var layoutManager: LinearLayoutManager? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.defi_fragment, container, false)
        loadCompany()

        val fragmentActivity = activity
        if (fragmentActivity != null)
            fragmentActivity.findViewById<View>(R.id.navigation).visibility = View.VISIBLE

        return view
    }

    private fun loadCompany() {
        mCompositeDisposable?.add(
                RetrofitApi.getRetrofitDeFi()!!.getDeFi()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::loadRecyclerView, this::initError)
        )
    }

    fun initError(error: Throwable){
        println(error)
    }

    fun loadRecyclerView(companies: DeFIModelResponce){
        loader.visibility = View.GONE
        var adapter = DeFiAdapter(companies.data, this)
        layoutManager = LinearLayoutManager(activity)
        rcvDeFi?.layoutManager = layoutManager
        rcvDeFi?.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                Constant.PRICE = adapter.companies[viewHolder.adapterPosition].quote.USD.price

                val fragment = TransactionFragment()
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.main_container, fragment).addToBackStack(null)
                transaction.commit()
            }
        })

        itemTouchHelper.attachToRecyclerView(rcvDeFi)

    }

    override fun OnClick(company: DeFiModel) {

    }
}