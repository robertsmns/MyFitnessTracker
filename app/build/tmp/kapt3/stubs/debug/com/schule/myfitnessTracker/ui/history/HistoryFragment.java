package com.schule.myfitnessTracker.ui.history;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\u001a\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u001d\u001a\u00020\u0019H\u0002J\u0010\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010!\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r\u00a8\u0006%"}, d2 = {"Lcom/schule/myfitnessTracker/ui/history/HistoryFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/schule/myfitnessTracker/databinding/FragmentHistoryBinding;", "binding", "getBinding", "()Lcom/schule/myfitnessTracker/databinding/FragmentHistoryBinding;", "runAdapter", "Lcom/schule/myfitnessTracker/ui/dashboard/RunHistoryAdapter;", "viewModel", "Lcom/schule/myfitnessTracker/ui/history/HistoryViewModel;", "getViewModel", "()Lcom/schule/myfitnessTracker/ui/history/HistoryViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "onResume", "onViewCreated", "view", "setupLiveStatus", "showDeleteConfirmDialog", "run", "Lcom/schule/myfitnessTracker/data/model/Run;", "showRunDetails", "updateLiveDuration", "timeFormat", "Ljava/text/SimpleDateFormat;", "app_debug"})
public final class HistoryFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.schule.myfitnessTracker.databinding.FragmentHistoryBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.schule.myfitnessTracker.ui.dashboard.RunHistoryAdapter runAdapter;
    
    public HistoryFragment() {
        super();
    }
    
    private final com.schule.myfitnessTracker.databinding.FragmentHistoryBinding getBinding() {
        return null;
    }
    
    private final com.schule.myfitnessTracker.ui.history.HistoryViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupLiveStatus() {
    }
    
    private final void updateLiveDuration(java.text.SimpleDateFormat timeFormat) {
    }
    
    private final void showDeleteConfirmDialog(com.schule.myfitnessTracker.data.model.Run run) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    private final void showRunDetails(com.schule.myfitnessTracker.data.model.Run run) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}