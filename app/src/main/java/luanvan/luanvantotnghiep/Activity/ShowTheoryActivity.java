package luanvan.luanvantotnghiep.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

import luanvan.luanvantotnghiep.R;

public class ShowTheoryActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private static final String FILENAME = "lop_8.pdf";
    private PDFView mPdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_theory);

        setupToolbar();

        mPdfView = findViewById(R.id.pdfView);
        mPdfView.fromAsset(FILENAME).load();
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Not use library
//    private ParcelFileDescriptor mFileDescriptor;
//    private PdfRenderer mPdfRenderer;
//
//    private ListView mLvPDF;
//    private List<Bitmap> mList;
//    private ShowPDFAdapter mAdapter;

    //    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mLvPDF = view.findViewById(R.id.lv_pdf);
//        mList = new ArrayList<>();
//        mAdapter = new ShowPDFAdapter(getActivity(), mList);
//        mLvPDF.setAdapter(mAdapter);
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        try {
//            openRenderer(getActivity());
//            showPage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void showPage() {
//
//        final int pageCount = mPdfRenderer.getPageCount();
//        for (int i = 0; i < pageCount; i++) {
//            PdfRenderer.Page page = mPdfRenderer.openPage(i);
//            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(),
//                    Bitmap.Config.ARGB_8888);
//            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//            mList.add(bitmap);
//            page.close();
//        }
//
//        mPdfRenderer.close();
//        mAdapter.notifyDataSetChanged();
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void openRenderer(Context context) throws IOException {
//
//        File file = new File(context.getCacheDir(), FILENAME);
//        if (!file.exists()) {
//
//            InputStream asset = context.getAssets().open(FILENAME);
//            FileOutputStream output = new FileOutputStream(file);
//            final byte[] buffer = new byte[1024];
//            int size;
//            while ((size = asset.read(buffer)) != -1) {
//                output.write(buffer, 0, size);
//            }
//            asset.close();
//            output.close();
//        }
//        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
//
//        if (mFileDescriptor != null) {
//            mPdfRenderer = new PdfRenderer(mFileDescriptor);
//        }
//    }
}
