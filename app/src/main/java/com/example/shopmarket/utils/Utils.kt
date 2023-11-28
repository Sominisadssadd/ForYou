package com.example.shopmarket.utils


fun String.removeFirstAndLastSymbols()  = this.removeRange(0,1).removeRange(this.length-2,this.length-1)
