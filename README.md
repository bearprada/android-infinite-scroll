android-infinite-scroll
======================

This project is inspired by `infinite-scroll` (https://github.com/paulirish/infinite-scroll) for jQuery/Wordpress plugin. and also our product Pic Collage (http://pic-collage.com) from Cardinal Blue (http://cardinalblue.com) requires this UI component on socail feature. please try out it!

this component can trigger event when move to the last item.

DEMO
====

you can try it on Google Play Store

https://play.google.com/store/apps/details?id=lab.prada.android.ui.mymodule.app

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

![](https://farm3.staticflickr.com/2925/13926159311_db68af5a70_d.jpg)
![](https://farm8.staticflickr.com/7187/13926159481_67ec4ed906_d.jpg)
![](https://farm6.staticflickr.com/5567/13926164336_452ccb2104_d.jpg)

TODO
====

1. add test case
2. update the project folder structure
3. support view pager


##License

    Copyright (c) 2014 PRADA Hsiung

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    Come on, don't tell me you read that.
