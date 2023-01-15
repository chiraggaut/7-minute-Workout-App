package com.example.a7minutesworkout

class ExerciseModel (
    private var id:Int,
    private var name:String,
    private var image: Int,
    private var isCompleted :Boolean,
    private var isSelected:Boolean
    ){
    fun getId():Int{
        return id
    }
    fun setId(id:Int){
        this.id = id
    }
    fun getName():String{
        return name
    }
    fun getImage():Int{
        return image
    }
    fun getIsSelected():Boolean{
        return isSelected
    }
    fun setIsSelected(a:Boolean){
        isSelected= a
    }
    fun getIsCompleted():Boolean{
        return isCompleted
    }

    fun setIsCompleted(b: Boolean) {
        isCompleted=b
    }
}