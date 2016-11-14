项目地址：https://github.com/mianmian520/FlowLayoutDemo
# FlowLayoutDemo
Android流式布局

### 效果图
![](https://github.com/mianmian520/FlowLayoutDemo/blob/master/imgs/image.png)

### 实现代码
#### 内部类一行的实现
      // 定义一个行的集合来存放子View
      private List<View> views = new ArrayList<>();
      // 行的最大宽度
      private int maxWidth;
      // 行中已经使用的宽度
      private int usedWidth;
      // 行的高度
      private int height;
      // 孩子之间的距离
      private float space;
      public void addView(View childView) {
          //获取到孩子节点的宽和高
          int childWidth = childView.getMeasuredWidth();
          int childHeight = childView.getMeasuredHeight();

          if(views.size() == 0){
              // 集合里没有孩子的时候
              if(childWidth > maxWidth){
                  usedWidth = maxWidth;
              } else {
                  usedWidth = childWidth;
              }
              height = childHeight;
          } else {
              usedWidth += childWidth + space;
              height = childHeight > height ? childHeight : height;
          }
          views.add(childView);
      }
      public boolean canAddView(View childView) {
          //集合没有数据
          if(views.size() == 0)
              return true;
          //获取到孩子节点的宽和高
          int childWidth = childView.getMeasuredWidth();
          //孩子的宽是否大于该行剩下的宽
          return maxWidth - usedWidth - space > childWidth ? true : false;
      }
      //指定孩子显示的位置
      public void layout(int l, int t) {
          //剩下的宽度平分
          int avg = (maxWidth - usedWidth) / views.size();
          for(View view : views){
              //获取到孩子节点的宽和高
              int childWidth = view.getMeasuredWidth();
              int childHeight = view.getMeasuredHeight();
              //把平分的宽度放到View中在重新测量
              view.measure(MeasureSpec.makeMeasureSpec(childWidth + avg, MeasureSpec.EXACTLY)
                          ,MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
              childWidth = view.getMeasuredWidth();
              // 指定位置
              view.layout(l, t, childWidth+l, childHeight+t);

              l += childWidth + space;
          }
      }
##### 测量
          // 遍历获取孩子
            int childCount = this.getChildCount();
            Log.i("test", "count:"+childCount);
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 测量孩子的宽和高
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);

                //测量完需要将孩子添加到管理行的孩子的集合中，将行添加到管理行的集合中
                if(line == null){
                    //第一次添加孩子
                    line = new Line(mMaxWidth, horizontal_space);
                    //添加孩子
                    line.addView(childView);
                    //添加行
                    mLines.add(line);
                } else {
                    if(line.canAddView(childView)){
                        line.addView(childView);
                    } else {
                        //添加到下一行
                        line = new Line(mMaxWidth, horizontal_space);
                        //添加孩子
                        line.addView(childView);
                        //添加行
                        mLines.add(line);
                    }
                }
            }
##### 位置
        @Override
        protected void onLayout(boolean b, int l, int t, int r, int buttom) {
            // 这里只负责高度的位置，具体的宽度和子孩子的位置让具体的行去管理

            l = getPaddingLeft();
            t = getPaddingTop();

            for (int k = 0 ; k < mLines.size() ; k++){
                Line line = mLines.get(k);
                line.layout(l, t);

                // 更新高度
                t += line.height;
                if (k != mLines.size() - 1) {
                    // 不是最后一条就添加间距
                    t += vertical_space;
                }
            }
        }
##### 适配器
      public void setAdapter(TagAdapter adapter){
              List<String> list = adapter.getmDataList();
              for (int i = 0 ; i < list.size() ; i++){
                  View view = adapter.getView(i, null, this);
                  addView(view);
                  final int finalI = i;
                  view.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          if(onTagItemClick != null){
                              onTagItemClick.onItemCilck(view, finalI);
                          }
                      }
                  });
              }
              invalidate();
          }
#####详细内容请下载源码 https://github.com/mianmian520/FlowLayoutDemo
