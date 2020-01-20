import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlin.math.log

/**
 * Created by wuyue on 2019/10/10.
 * description:
 */

class KotlinActivity : AppCompatActivity() {
    var view: String= "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val e =User.sit
    }

//    get(){
//        log("N")
//        return field
//    }
//
//    set(value){
//        field =value;
//        log("E")
//    }


}

class User{
    var user: String


    constructor(_user :String){
        this.user =_user
    }

    companion object  {
        var sit ="qqq"
    }
    init {
        val sit ="asd"
        this.user="qweq"
        println()
    }



}
