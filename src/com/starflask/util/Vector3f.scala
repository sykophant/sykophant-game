package com.starflask.util

  class Vector3f(val x:Float = 0.0f, val y:Float = 0.0f, val z:Float = 0.0f){
     
   def add(v:Vector3f) = new Vector3f(x + v.x, y + v.y, z + v.z)

    def subtract(v:Vector3f) = new Vector3f(x - v.x, y - v.y, z - v.z)
  
   def dot(v:Vector3f) = x * v.x + y * v.y + z * v.z

    def scale(n:Float) = new Vector3f(x * n, y * n, z * n)
   
   
    def normalize = {
        val l = length
        new Vector3f((x/l).toFloat, (y/l).toFloat, (z/l).toFloat)
    }
   
   def lengthSquared = x * x + y * y + z * z   //wow scala is realy cool 
   
    def length = Math.sqrt(lengthSquared)
   
    def negate = new Vector3f(-x, -y, -z)
}