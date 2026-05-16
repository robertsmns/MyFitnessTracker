package com.schule.myfitnessTracker.ui.dashboard;

/**
 * Adapter für die Trainings-Historie in der RecyclerView.
 *
 * Zeigt für jeden Run:
 * - Datum & Uhrzeit
 * - Distanz und Dauer
 * - Durchschnittsgeschwindigkeit
 * - Schritte
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u0012B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\n\u001a\u00020\u00062\n\u0010\u000b\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u000e\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\rH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/schule/myfitnessTracker/ui/dashboard/RunHistoryAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/schule/myfitnessTracker/data/model/Run;", "Lcom/schule/myfitnessTracker/ui/dashboard/RunHistoryAdapter$RunViewHolder;", "onDeleteClick", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "dateFormat", "Ljava/text/SimpleDateFormat;", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "RunViewHolder", "app_debug"})
public final class RunHistoryAdapter extends androidx.recyclerview.widget.ListAdapter<com.schule.myfitnessTracker.data.model.Run, com.schule.myfitnessTracker.ui.dashboard.RunHistoryAdapter.RunViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.schule.myfitnessTracker.data.model.Run, kotlin.Unit> onDeleteClick = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormat = null;
    
    public RunHistoryAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.schule.myfitnessTracker.data.model.Run, kotlin.Unit> onDeleteClick) {
        super(null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.schule.myfitnessTracker.ui.dashboard.RunHistoryAdapter.RunViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.ui.dashboard.RunHistoryAdapter.RunViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/schule/myfitnessTracker/ui/dashboard/RunHistoryAdapter$RunViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/schule/myfitnessTracker/databinding/ItemRunBinding;", "(Lcom/schule/myfitnessTracker/ui/dashboard/RunHistoryAdapter;Lcom/schule/myfitnessTracker/databinding/ItemRunBinding;)V", "bind", "", "run", "Lcom/schule/myfitnessTracker/data/model/Run;", "app_debug"})
    public final class RunViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.schule.myfitnessTracker.databinding.ItemRunBinding binding = null;
        
        public RunViewHolder(@org.jetbrains.annotations.NotNull()
        com.schule.myfitnessTracker.databinding.ItemRunBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.schule.myfitnessTracker.data.model.Run run) {
        }
    }
}