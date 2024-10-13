package work.jsfr.uuidgenerator.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import work.jsfr.uuidgenerator.R

class AboutFragment : Fragment() {
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        textView = view.findViewById(R.id.textView)

        // 设置TextView内容
        setupTextView()

        return view
    }

    private fun setupTextView() {
        textView.text = getString(R.string.about_url)
    }
}