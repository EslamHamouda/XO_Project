package com.example.tictactoe.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentOnlineGameBinding
import com.google.firebase.database.*
import java.lang.NumberFormatException


class OnlineGameFragment : Fragment() {

    private lateinit var binding : FragmentOnlineGameBinding
    var player1=ArrayList<Int>()
    var player2=ArrayList<Int>()
    private var activePlayer=1

    val buttons= ArrayList<Button>()

    val fireDB:DatabaseReference=FirebaseDatabase.getInstance().reference.child("game")

    var lock=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentOnlineGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttons.add(binding.button1)
        buttons.add(binding.button2)
        buttons.add(binding.button3)
        buttons.add(binding.button4)
        buttons.add(binding.button5)
        buttons.add(binding.button6)
        buttons.add(binding.button7)
        buttons.add(binding.button8)
        buttons.add(binding.button9)

        fireDB.child("cellID").addChildEventListener(object:ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                playGame(snapshot.key!!.toInt(),buttons[snapshot.key!!.toInt()-1])

            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        fireDB.child("activePlayer").addChildEventListener(object:ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



    private fun buttonClick()
    {
            for (item in buttons) {
                item.setOnClickListener { v ->
                    var selectedButton: Button? = null
                    var cellId = 0

                    when (v.id) {
                        R.id.button1 -> {
                            cellId = 1;selectedButton = binding.button1
                        }
                        R.id.button2 -> {
                            cellId = 2;selectedButton = binding.button2
                        }
                        R.id.button3 -> {
                            cellId = 3;selectedButton = binding.button3
                        }
                        R.id.button4 -> {
                            cellId = 4;selectedButton = binding.button4
                        }
                        R.id.button5 -> {
                            cellId = 5;selectedButton = binding.button5
                        }
                        R.id.button6 -> {
                            cellId = 6;selectedButton = binding.button6
                        }
                        R.id.button7 -> {
                            cellId = 7;selectedButton = binding.button7
                        }
                        R.id.button8 -> {
                            cellId = 8;selectedButton = binding.button8
                        }
                        R.id.button9 -> {
                            cellId = 9;selectedButton = binding.button9
                        }
                    }
                    playGame(cellId, selectedButton!!)
                }
            }
        buttonClick()
    }


    private fun playGame(cellId:Int, selectedButton: Button)
    {
        playerTurn()
        if(activePlayer==1)
        {
            selectedButton.text="X"
            player1.add(cellId)
            //activePlayer=2
            try {
                fireDB.child("activePlayer").setValue(2)
                fireDB.child("activePlayer").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        activePlayer=snapshot.value.toString().toInt()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }catch (e:NumberFormatException){
                Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        else if(activePlayer==2)
        {
            selectedButton.text="O"
            player2.add(cellId)
           // activePlayer=2
            try {
                fireDB.child("activePlayer").setValue(1)
                fireDB.child("activePlayer").addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        activePlayer=snapshot.value.toString().toInt()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }catch (e:NumberFormatException){
                Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        selectedButton.isEnabled=false
        checkWinner()
    }


    private fun checkWinner()
    {
        var winner=-1

        //row1
        if(player1.contains(1) && player1.contains(2) && player1.contains(3))
            winner=1
        else if (player2.contains(1) && player2.contains(2) && player2.contains(3))
            winner=2

        //row2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6))
            winner=1
        else if (player2.contains(4) && player2.contains(5) && player2.contains(6))
            winner=2

        //row3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9))
            winner=1
        else if (player2.contains(7) && player2.contains(8) && player2.contains(9))
            winner=2


        //column1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7))
            winner=1
        else if (player2.contains(1) && player2.contains(4) && player2.contains(7))
            winner=2

        //column2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8))
            winner=1
        else if (player2.contains(2) && player2.contains(5) && player2.contains(8))
            winner=2

        //column3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9))
            winner=1
        else if (player2.contains(3) && player2.contains(6) && player2.contains(9))
            winner=2

        //diagonal 1
        if(player1.contains(1) && player1.contains(5) && player1.contains(9))
            winner=1
        else if (player2.contains(1) && player2.contains(5) && player2.contains(9))
            winner=2

        //diagonal 2
        if(player1.contains(3) && player1.contains(5) && player1.contains(7))
            winner=1
        else if (player2.contains(3) && player2.contains(5) && player2.contains(7))
            winner=2

        if(winner!=-1)
        {
            if(winner==1)
                alertDialog("Player X is winner! 🥳")
            else if (winner==2)
                alertDialog("Player O is winner! 🥳")
        }
        if (emptyCheck())
        {
            alertDialog("Great players! 👌")
        }

    }

    private fun playerTurn(){
        if (activePlayer==1){binding.player.text="X is played"}else{binding.player.text="O is played"}
    }

    private fun alertDialog(text:String){
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialog)
        builder.setTitle(text)

        builder.setPositiveButton("OK") { dialog, i ->
            requireView().findNavController().popBackStack()
        }

        val alert = builder.create()
        alert.show()
        alert.window!!.setLayout(800, 350)
    }

    private fun emptyCheck():Boolean{
        if (  binding.button1.text!=""&&
            binding.button2.text!=""&&
            binding.button3.text!=""&&
            binding.button4.text!=""&&
            binding.button5.text!=""&&
            binding.button6.text!=""&&
            binding.button7.text!=""&&
            binding.button8.text!=""&&
            binding.button9.text!="")
        {
            return true
        }
        return false
    }
}