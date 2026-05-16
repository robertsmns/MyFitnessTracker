package com.schule.myfitnessTracker.ui.tracking;

/**
 * Karten-Fragment – zeigt die live gezeichnete Route auf Google Maps.
 *
 * Features:
 * - Karte zoomt automatisch auf aktuellen Standort
 * - Route wird als blaue Polylinie gezeichnet
 * - Start-Marker (grün) und aktuelle Position (blau)
 * - Start/Stop-Button zum Steuern des Trackings
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001cH\u0002J$\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020\u001cH\u0016J\u0010\u0010\'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\u000eH\u0016J\u001a\u0010)\u001a\u00020\u001c2\u0006\u0010*\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010+\u001a\u00020\u001cH\u0002J\b\u0010,\u001a\u00020\u001cH\u0002J\u0018\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018\u00a8\u00061"}, d2 = {"Lcom/schule/myfitnessTracker/ui/tracking/MapFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "_binding", "Lcom/schule/myfitnessTracker/databinding/FragmentMapBinding;", "binding", "getBinding", "()Lcom/schule/myfitnessTracker/databinding/FragmentMapBinding;", "currentMarker", "Lcom/google/android/gms/maps/model/Marker;", "isFirstLocationUpdate", "", "map", "Lcom/google/android/gms/maps/GoogleMap;", "polyline", "Lcom/google/android/gms/maps/model/Polyline;", "routePoints", "", "Lcom/schule/myfitnessTracker/data/model/RoutePoint;", "startMarker", "viewModel", "Lcom/schule/myfitnessTracker/ui/tracking/TrackingViewModel;", "getViewModel", "()Lcom/schule/myfitnessTracker/ui/tracking/TrackingViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "centerOnCurrentLocation", "", "observeViewModel", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onMapReady", "googleMap", "onViewCreated", "view", "redrawRoute", "setupButtons", "zoomToPosition", "lat", "", "lng", "app_debug"})
public final class MapFragment extends androidx.fragment.app.Fragment implements com.google.android.gms.maps.OnMapReadyCallback {
    @org.jetbrains.annotations.Nullable()
    private com.schule.myfitnessTracker.databinding.FragmentMapBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.GoogleMap map;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.model.Polyline polyline;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.model.Marker startMarker;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.model.Marker currentMarker;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint> routePoints = null;
    private boolean isFirstLocationUpdate = true;
    
    public MapFragment() {
        super();
    }
    
    private final com.schule.myfitnessTracker.databinding.FragmentMapBinding getBinding() {
        return null;
    }
    
    private final com.schule.myfitnessTracker.ui.tracking.TrackingViewModel getViewModel() {
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
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap googleMap) {
    }
    
    private final void setupButtons() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void redrawRoute() {
    }
    
    private final void zoomToPosition(double lat, double lng) {
    }
    
    private final void centerOnCurrentLocation() {
    }
}