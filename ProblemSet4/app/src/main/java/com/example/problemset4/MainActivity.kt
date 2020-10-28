package com.example.problemset4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "mainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var resetButton: Button
    private val ticTacToeBoard = mutableListOf("0", "0", "0", "0", "0", "0", "0", "0", "0")
    private var turnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recycler)
        resetButton = findViewById(R.id.reset_button)

        recycler.layoutManager = GridLayoutManager(this, 3)
        recycler.adapter = TicTacToeAdapter()

        //https://developer.android.com/reference/android/app/Activity.html#recreate()
        resetButton.setOnClickListener { this.recreate() }
    }

    private inner class TicTacToeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.tic_tac_toe_button)
        private var boardPosition = 0

        fun bind(p: Int) { boardPosition = p }

        init {
            button.setOnClickListener {
                // disable button
                button.isEnabled = false
                button.isClickable = false

                if (isEven(turnCount)) ticTacToeBoard[boardPosition] = "X"
                else ticTacToeBoard[boardPosition] = "O"

                recycler.adapter?.notifyItemChanged(boardPosition)
                turnCount++

            }
        }

        private fun isEven(number: Int): Boolean { return number % 2 == 0 }
    }

    private inner class TicTacToeAdapter: RecyclerView.Adapter<TicTacToeViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicTacToeViewHolder {
            val view = layoutInflater.inflate(R.layout.tic_tac_toe_slot, parent, false)
            return TicTacToeViewHolder(view)
        }

        override fun onBindViewHolder(holder: TicTacToeViewHolder, position: Int) {
            holder.bind(position)

            // set images
            when {
                ticTacToeBoard[position] == "0" -> holder.button.setBackgroundResource(R.drawable.empty)
                ticTacToeBoard[position] == "X" -> holder.button.setBackgroundResource(R.drawable.x_sign)
                ticTacToeBoard[position] == "O" -> holder.button.setBackgroundResource(R.drawable.o_sign)
            }
        }

        override fun getItemCount(): Int { return 9 }
    }
}