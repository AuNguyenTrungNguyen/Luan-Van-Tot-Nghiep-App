package luanvan.luanvantotnghiep.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import luanvan.luanvantotnghiep.R;

public class MainFragment extends Fragment {
//    private ParcelFileDescriptor mFileDescriptor;
//    private PdfRenderer mPdfRenderer;
//
//    private ListView mLvPDF;
//    private List<Bitmap> mList;
//    private ShowPDFAdapter mAdapter;

    private static final String FILENAME = "lop_8.pdf";
    private PDFView mPdfView;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
//        mPdfView = v.findViewById(R.id.pdfView);
//
//        mPdfView.fromAsset(FILENAME).load();

        return v;
    }

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
