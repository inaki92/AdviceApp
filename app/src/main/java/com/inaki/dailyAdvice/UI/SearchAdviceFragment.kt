package com.inaki.dailyAdvice.UI

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inaki.dailyAdvice.Adapters.AdviceSearchedAdapter
import com.inaki.dailyAdvice.Model.AdviceModel

import com.inaki.dailyAdvice.R
import com.inaki.dailyAdvice.ViewModel.AdviceViewModel
import kotlinx.android.synthetic.main.fragment_search_advice.view.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchAdviceFragment : Fragment() {
    private var topic: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var advice: AdviceModel
    private lateinit var searchedRecycler: RecyclerView
    private lateinit var adviceAdapter: AdviceSearchedAdapter

    private val adviceViewModel: AdviceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topic = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val searchView = inflater.inflate(R.layout.fragment_search_advice, container, false)

        searchView.setBackgroundColor(Color.GREEN)
        searchedRecycler = searchView.recycler_advice_searched
        searchedRecycler.setHasFixedSize(true)
        searchedRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        searchView.search_advice.setOnQueryTextListener(object:
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                topic = query
                viewModelLoadData()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return searchView
    }

    private fun viewModelLoadData() {
        adviceViewModel.getSearchedAdvices(topic!!).observe(this, Observer { adviceList ->
            if (adviceList.totalResults.isNullOrEmpty()){
                Toast.makeText(context,"Topic Not Found",Toast.LENGTH_SHORT).show()
            }else{
            advice = adviceList
            adviceAdapter = AdviceSearchedAdapter(context!!,advice)
            searchedRecycler.adapter = adviceAdapter
            }
        })
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchAdviceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
