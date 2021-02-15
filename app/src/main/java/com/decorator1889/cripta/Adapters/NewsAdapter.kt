
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.decorator1889.cripta.Models.NewsModel
import com.decorator1889.cripta.R
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso


class NewsAdapter(val companies: ArrayList<NewsModel>, var listener: Clicker): RecyclerView.Adapter<NewsAdapter.ViewHolder>(){


    interface Clicker{
        fun OnClick(company: NewsModel)
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.card_news, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  companies.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.insertAddons(companies[p1], listener)

        p0.source?.text = companies[p1].source


        if (p1%3 == 0){
            p0.title2?.text = companies[p1].title
            p0.title?.visibility  = GONE
            p0.title2?.visibility  = VISIBLE

            p0.image?.visibility  = VISIBLE
            p0.image2?.visibility  = GONE
        Picasso.get()
            .load(companies[p1].imageurl)
            .placeholder(R.drawable.button_background_defult)
            .error(R.drawable.ic_launcher_background)
            .into(p0.image)
        }

        else{
            p0.title?.text = companies[p1].title
            p0.title2?.visibility  = GONE
            p0.title?.visibility  = VISIBLE

            p0.image2?.visibility  = VISIBLE
            p0.image?.visibility = GONE
            Picasso.get()
                .load(companies[p1].imageurl)
                .placeholder(R.drawable.button_background_defult)
                .error(R.drawable.ic_launcher_background)
                .into(p0.image2)
        }

        p0.cardView?.setOnClickListener {listener.OnClick(companies[p1])}
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //var body: TextView? = null
        var title: TextView? = null
        var title2: TextView? = null
        var image: ImageView? = null
        var cardView: CardView? = null
        var source: TextView? = null
        var image2: ImageView? = null
        //    var imageLogo: PorterShapeImageView? = null
        @SuppressLint("NewApi", "WrongViewCast")
        fun insertAddons(companies: NewsModel, listener: Clicker){

            cardView = itemView.findViewById(R.id.cardNews) as CardView
            title = itemView.findViewById(R.id.tvTitle) as TextView
            title2 = itemView.findViewById(R.id.tvTitle2) as TextView
            //body = itemView.findViewById(R.id.tvBody) as TextView
            image = itemView.findViewById(R.id.ivNews) as ImageView
            image2 = itemView.findViewById(R.id.ivNews2) as ImageView
            source = itemView.findViewById(R.id.tvSource) as TextView

        }
    }
}