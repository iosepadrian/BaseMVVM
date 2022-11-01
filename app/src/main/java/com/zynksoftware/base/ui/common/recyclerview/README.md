# Custom RecyclerView and Adapters

Since almost every app has a list, most of them are paginated I decided to create a custom recyclerview for easier implementation (I hope).
This example will include SwipeToRefreshLayout, Simple Adapter, Paginated Adapter with loading/error footer. Base class is `BaseRecyclerView`

### Let's start with Simple Adapter:
- extend `BaseAdater` with Model, Binding and Comparator, then you will need to override onBind

    class DemoAdapter(  
        private val itemClick: (DemoModel) -> Unit? = {}  
    ): BaseAdapter<DemoModel, ViewHolderDemoBinding>(ViewHolderDemoBinding::inflate, Comparator) {  
      
        override fun ViewHolderDemoBinding.onBind(context: Context, item: DemoModel, position: Int) {  
	        textView.text = item.title  
		    rootView.setOnClickListener {  
		        itemClick.invoke(item)  
            }  
        }  
      
        object Comparator : DiffUtil.ItemCallback<DemoModel>() {  
            override fun areItemsTheSame(oldItem: DemoModel, newItem: DemoModel) =  
                oldItem.id == newItem.id  
      
		    override fun areContentsTheSame(oldItem: DemoModel, newItem: DemoModel) =  
			      oldItem.title == newItem.title  
		    }  
    }

In Activity/Fragment you can have something like:

    val demoAdapter = DemoAdapter(itemClick = { item ->
        
    })  
      
    observe(viewModel.listLiveData) {  
        demoAdapter.submitList(it)  
        binding.simpleRecyclerView.isRefreshing = false  
    }  
      
    binding.simpleRecyclerView.setLayoutManager(LinearLayoutManager(this))  
    binding.simpleRecyclerView.setAdapter(demoAdapter, swipeRefreshListener = {  
        viewModel.getList(isFromRefresh = true)  
    })


### Complex Adapter
This is an infinite list with loading/error state

Adapter:

    class DemoInfiniteAdapter(  
        private val itemClick: (DemoModel, Int) -> Unit,  
     private val itemLongClick: (DemoModel, Int) -> Unit  
    ): PagingBaseAdapter<DemoModel, ViewHolderDemoBinding>(ViewHolderDemoBinding::inflate, Comparator) {  
        override fun ViewHolderDemoBinding.onBind(item: DemoModel?, context: Context, position: Int) {  
            item?.let {  
      textView.text = it.title  
     rootView.setOnClickListener {  
      itemClick.invoke(item, position)  
                }  
      rootView.setOnLongClickListener {  
      itemLongClick.invoke(item, position)  
                    true  
      }  
     }  }  
      
        object Comparator : DiffUtil.ItemCallback<DemoModel>() {  
            override fun areItemsTheSame(oldItem: DemoModel, newItem: DemoModel) =  
                oldItem.id == newItem.id  
      
      override fun areContentsTheSame(oldItem: DemoModel, newItem: DemoModel) =  
                oldItem.title == newItem.title  
      }  
    }

You will need to add layout `R.layout.view_holder_item_network_state` and customize based on your design but keep ids of the views, or you can change it and adjust `PagingLoadStateAdapter` class.

