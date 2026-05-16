package com.schule.myfitnessTracker.ui;

/**
 * Haupt-Activity – Container für alle Fragments.
 *
 * Verwaltet:
 * - Navigation über Bottom Navigation Bar
 * - Berechtigungsanfragen (GPS, Aktivitätserkennung, Benachrichtigungen)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u000eH\u0002J\b\u0010\u0012\u001a\u00020\u000eH\u0002J\b\u0010\u0013\u001a\u00020\u000eH\u0002R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\nX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\f\u00a8\u0006\u0014"}, d2 = {"Lcom/schule/myfitnessTracker/ui/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "backgroundLocationLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "binding", "Lcom/schule/myfitnessTracker/databinding/ActivityMainBinding;", "permissionLauncher", "", "requiredPermissions", "[Ljava/lang/String;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "requestBackgroundLocation", "requestPermissionsIfNeeded", "setupNavigation", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.schule.myfitnessTracker.databinding.ActivityMainBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String[] requiredPermissions = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> permissionLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> backgroundLocationLauncher = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupNavigation() {
    }
    
    private final void requestPermissionsIfNeeded() {
    }
    
    private final void requestBackgroundLocation() {
    }
}