package fauzi.hilmy.kadeSubmission1.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import fauzi.hilmy.kadeSubmission1.R
import fauzi.hilmy.kadeSubmission1.R.array.*
import fauzi.hilmy.kadeSubmission1.R.id.img_club
import fauzi.hilmy.kadeSubmission1.R.id.txtNamaClub
import fauzi.hilmy.kadeSubmission1.model.Club
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.wrapContent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val clubs: MutableList<Club> = mutableListOf()

        val nama = resources.getStringArray(clubName)
        val image = resources.getStringArray(clubImage)
        val desc = resources.getStringArray(clubDescription)

        clubs.clear()

        for (i in nama.indices) {
            clubs.add(Club(nama[i], image[i], desc[i]))
        }

        verticalLayout {
            recyclerView {
                lparams(width = matchParent, height = matchParent)
                layoutManager = LinearLayoutManager(context)
                adapter = ClubAdapter(clubs) {
                    startActivity<DetailActivity>(
                            "nama" to it.clubNama,
                            "image" to it.clubImage,
                            "desc" to it.clubDesc
                    )
                }
            }
        }
    }

    class ClubAdapter(private val clubs: List<Club>, private val listener: (Club) -> Unit) : RecyclerView.Adapter<ClubAdapter.MyViewHolder>() {

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindItem(clubs[position], listener)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(ClubUI().createView(AnkoContext.create(parent.context, parent)))
        }

        override fun getItemCount(): Int = clubs.size

        class ClubUI : AnkoComponent<ViewGroup> {

            override fun createView(ui: AnkoContext<ViewGroup>): View {
                return with(ui) {
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        padding = dip(16)

                        imageView {
                            id = R.id.img_club
                        }.lparams(width = dip(60), height = dip(60))

                        textView {
                            id = R.id.txtNamaClub
                            textSize = 16f
                        }.lparams(width = wrapContent, height = wrapContent) {
                            gravity = Gravity.CENTER_VERTICAL
                            margin = dip(8)
                        }
                    }
                }
            }
        }

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val namaClubAdap: TextView = view.find(txtNamaClub)
            private val imageClubAdap: ImageView = view.find(img_club)
            fun bindItem(clubs: Club, listener: (Club) -> Unit) {
                namaClubAdap.text = clubs.clubNama
                Glide.with(itemView.context)
                        .load(clubs.clubImage)
                        .into(imageClubAdap)
                itemView.onClick { listener(clubs) }
            }
        }
    }
}
