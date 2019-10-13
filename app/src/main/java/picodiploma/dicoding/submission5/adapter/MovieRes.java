package picodiploma.dicoding.submission5.adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import picodiploma.dicoding.submission5.detail.MovieItem;

public class MovieRes {
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_results")
    @Expose
    private int totalResult;

    @SerializedName("results")
    @Expose
    private ArrayList<MovieItem> result = null;

    @SerializedName("total_pages")
    @Expose
    private int totalPage;

    public MovieRes(int page, int totalResult, int totalPage, ArrayList<MovieRes> movieResult) {
        this.page = page;
        this.totalResult = totalResult;
        this.totalPage = totalPage;
        movieResult = movieResult;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public ArrayList<MovieItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<MovieItem> movies) {
        this.result = result;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
