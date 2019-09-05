package com.inaki.dailyAdvice.UI

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.inaki.dailyAdvice.Model.GameAdviceModel
import com.inaki.dailyAdvice.R
import com.inaki.dailyAdvice.Util.NO_MORE
import com.inaki.dailyAdvice.ViewModel.AdviceViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_game_advice.*
import kotlinx.android.synthetic.main.fragment_game_advice.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GameAdviceFragment : Fragment() {
    private var id: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var advice: GameAdviceModel

    private val gameViewModel: AdviceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gameView = inflater.inflate(R.layout.fragment_game_advice, container, false)

        RxTextView.afterTextChangeEvents(gameView.number_user)
            .skipInitialValue()
            .map { gameView.number_user.error = null
                it.view().text.toString()
            }
            .debounce(400,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
            .compose(validateNumber)
            .compose( retryWhenError { gameView.number_user.error = it.message })
            .subscribe()

        gameView.start_btn.setOnClickListener {
            if (gameView.number_user.text.toString().isNotEmpty()) {
                    fetchQuoteWithId()
                }else{
                QuoteDialog(resources.getString(R.string.error_with_number))
                    .show(fragmentManager,"Dialog")
            }
        }

        return gameView
    }

    private val validateNumber = ObservableTransformer<String, String>{ observable ->
        observable.flatMap { Observable.just(it).map { it.trim() } }
            .filter { it.length > 3  }
            .singleOrError()
            .onErrorResumeNext {
                Toast.makeText(context,"Your number must be 3 characters or less",Toast.LENGTH_SHORT).show()
                if (it is NoSuchElementException){
                    Toast.makeText(context,"Your number must be 3 characters or less",Toast.LENGTH_SHORT).show()
                    Single.error(Exception("Your number must be 3 characters or less"))
                }else{
                    Single.error(it)
                }
            }.toObservable()
    }

    private inline fun retryWhenError(crossinline onError: (ex: Throwable) -> Unit):
            ObservableTransformer<String, String> =
        ObservableTransformer { observable ->
            observable.retryWhen { errors ->
                errors.flatMap {
                    onError(it)
                    Observable.just("")
                }
            }
        }

    private fun fetchQuoteWithId() {
        id = number_user.text.toString()
        gameViewModel.getGameAdvice(id!!).observe(this, Observer { gameAdvice ->
            when (id){
                in "1".."218" -> {
                    advice = gameAdvice
                    QuoteDialog(advice.slip.advice).show(fragmentManager,"Dialog")
                }
                in "3".."9" -> {
                    advice = gameAdvice
                    QuoteDialog(advice.slip.advice).show(fragmentManager,"Dialog")
                }
                else -> { QuoteDialog(NO_MORE).show(fragmentManager,"Dialog") }
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
            GameAdviceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
