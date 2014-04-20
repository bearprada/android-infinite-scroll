android-infinite-scroll
======================

This project is inspired by `infinite-scroll` (https://github.com/paulirish/infinite-scroll) for jQuery/Wordpress plugin. and also our product Pic Collage (pic-collage.com) from Cardinal Blue (http://cardinalblue.com) requires this UI component on socail feature. please try out it!

this component can trigger event when move to the last item.


Usage
=====

Constructor
-----------

You can assign a customized progress view.

```
RelativeLayout progress = new RelativeLayout(this);
progress.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 100));
progress.setGravity(Gravity.CENTER);
progress.addView(new ProgressBar(this));

mAdapter = new ReadMoreAdapter<SampleAdapter>(this,
      new SampleAdapter(this), progress);
((ListView)findViewById(R.id.listView)).setAdapter(mAdapter);
```

Or you can use the default option, but you should assign the width and height

```
mGridView = (GridView)findViewById(R.id.gridView);
mAdapter = new ReadMoreAdapter<SampleAdapter>(this,
      new SampleAdapter(this), GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT);
mGridView.setAdapter(mAdapter);
```

Listener
--------

it's trigger when the view is scroll to the bottom.

```
mAdapter.setListener(new PullDownToRefreshListener() {
    public void pullDownToRefresh() {
         // Do something and apply the result into adapter
         mAdapter.handledRefresh();
    }
});
```


Screenshot
==========



TODO
====

1. add test case
2. update the project folder structure
3. support view pager
