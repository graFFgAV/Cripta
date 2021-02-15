
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.decorator1889.cripta.Models.MarketsModel
import com.decorator1889.cripta.R
import com.squareup.picasso.Picasso


class EcomAdapter(val companies: ArrayList<MarketsModel>, var listener: Clicker): RecyclerView.Adapter<EcomAdapter.ViewHolder>() {


    interface Clicker{
        fun OnClick(company: MarketsModel)
    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        var view = LayoutInflater.from(p0.context).inflate(R.layout.list_category, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  companies.size
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


            p0.insertAddons(companies[p1], listener)
            p0.name?.text = companies[p1].name
            p0.sizeActions?.text = companies[p1].priceUsd
            val i = companies[p1].changePercent24Hr.toDouble()
            println(i)
            val j = companies[p1].priceUsd.toDouble()
            val solutionForPercentage = String.format("%.2f", i)
            val solutionForPrice = String.format("%.2f", j)
            p0.tvPerc?.text = solutionForPercentage + "%"
            p0.sizeActions?.text = "$" + solutionForPrice
            if (i > 0) {
                p0.tvPerc?.setBackgroundResource(R.drawable.rounded_corner_txt_plus)
            } else {
                p0.tvPerc?.setBackgroundResource(R.drawable.rounded_corner_txt_minus)
            }

            Picasso.get()
                    .load("https://static.coincap.io/assets/icons/" + companies[p1].symbol.toLowerCase() + "@2x.png")
                    .error(R.drawable.ic_launcher_background)
                    .resize(100, 100).centerCrop()
                    .into(p0.imageLogo);

            p0.cardView?.setOnClickListener { listener.OnClick(companies[p1]) }

        }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var sizeActions: TextView? = null
        var tvPerc: TextView? = null
        var cardView: CardView? = null
        var imageLogo: ImageView? = null
        @SuppressLint("NewApi", "WrongViewCast")
        fun insertAddons(companies: MarketsModel, listener: Clicker){
            name = itemView.findViewById(R.id.tvName) as TextView
            cardView = itemView.findViewById(R.id.cardViewCategory) as CardView
            sizeActions = itemView.findViewById(R.id.tvAmount) as TextView
            tvPerc = itemView.findViewById(R.id.tvPercentage) as TextView
            imageLogo = itemView.findViewById(R.id.ivIconCategory) as ImageView
        }
    }
}


