
package com.lib.commui.filebrowser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.commui.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 文件浏览
 */
public class FolderBrowser extends Activity implements OnCheckedChangeListener {
    private static final String t = "FileBrowser";

    /**
     * 删除确认
     */
    private static final int DELETEFILE = 2;

    /**
     * 启动后自动进行文件搜索
     */
    public static final String AUTO_FLAG = "AUTO_FLAG";

    /**
     * 显示文件模式
     *
     * @author Administrator
     */
    private enum DISPLAYMODE {
        ABSOLUTE, RELATIVE;
    }

    private final DISPLAYMODE mDisplayMode = DISPLAYMODE.RELATIVE;

    private List<IconText> mDirectoryList = new ArrayList<IconText>();

    private File mCurrentDirectory = new File("/sdcard/");
    private IconTextListAdapter mDirectoryListAdapter;

    private final static int searchCount = 60;

    private static boolean IS_CONTINUE = true;

    //进度对话框
    private ProgressDialog progressDialog;

    private ListView listFileBrowser;
    private static List<IconText> listFileBrowserAutoContent = new ArrayList<IconText>();


    private View emptyLayout1;

    private View btnReturn;

    private TextView title;

    private String FILE_PATH;


    //-------------------------------------------------------------------


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // 隐去标题栏（程序的名字）
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.folders_list);
        title = (TextView) findViewById(R.id.title);

        mDirectoryListAdapter = new IconTextListAdapter(this);
        // 初始化LIst
        listFileBrowser = (ListView) findViewById(R.id.listFileBrowser);

        listFileBrowser.setOnItemClickListener(new ListOnItemClick());
        listFileBrowser.setOnItemLongClickListener(onItemLongClickListener);

        //空
        emptyLayout1 = findViewById(R.id.empty1_layout);

        //初始化按钮
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new BtnReturnOnClickListener());


        findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnOk).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("path", mCurrentDirectory.getPath());
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD卡挂载错误", Toast.LENGTH_SHORT).show();
        }


        try {
            //浏览文件

            File fpath = Environment.getExternalStorageDirectory();
            if (getIntent() != null && getIntent().getStringExtra("path") != null) {
                String path = getIntent().getStringExtra("path");
                if (!path.equals("")) {
                    File nowPath = new File(path);

                    if (nowPath.exists()) {
                        fpath = nowPath;
                    }
                }
            }

            Log.d(t, "fpath=" + fpath.getPath());

            browseToWhere(fpath);


        } catch (Exception ex) {
            Log.e(t, "onCreate read sd card error.", ex);

            //---------------------------------------------------------------
            new AlertDialog.Builder(this)
                    .setMessage("step:7" + ", " + ex.getMessage() + ", /n" + ex.toString())
                    .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
            //---------------------------------------------------------------
        }
    }


    public void onClickQuick(View v) {
        String root = Environment.getExternalStorageDirectory().getPath();

        String cpath = "";
        if (v.getId() == R.id.btnSDCard) {
            cpath = root;
        } else if (v.getId() == R.id.btnWeixin) {
            cpath = root + "/Android/data/com.tencent.mm/MicroMsg/Download"; //   /tencent/MicroMsg/Download
        } else if (v.getId() == R.id.btnQq) {
            cpath = root + "/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv";  //   /tencent/QQfile_recv
        } else if (v.getId() == R.id.btnTim) {
            cpath = root + "/tencent/TIMfile_recv";
        }

        browseToWhere(new File(cpath));
    }


    //返回
    public class BtnReturnOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 返回上级目录
     */
    private void upOneLevel() {
        if (!this.mCurrentDirectory.getParent().equals("/"))
            this.browseToWhere(this.mCurrentDirectory.getParentFile());
    }

    private void browseToWhere(final File aDirectory) {
        if (this.mDisplayMode == DISPLAYMODE.RELATIVE)
            this.setTitle(aDirectory.getAbsolutePath());

        //标题显示当前路径
        //title.setText(aDirectory.getPath());
        String path = aDirectory.getPath();
        path = path.replace(Environment.getExternalStorageDirectory().getPath(), "SD卡");
        Log.d(t, "path=" + path + ", sd=" + Environment.getExternalStorageDirectory().getPath());
        title.setText(path);

        if (aDirectory.isDirectory()) {
            this.mCurrentDirectory = aDirectory;
            //fill(aDirectory.listFiles());

            List<File> files = Arrays.asList(aDirectory.listFiles());
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (o1.isDirectory() && o2.isFile())
                        return -1;
                    if (o1.isFile() && o2.isDirectory())
                        return 1;
                    return o1.getName().compareTo(o2.getName());
                }
            });

            fill(files);
        } else {
        }
    }

    private void fill(List<File> files) {
        if (files == null)
            return;

        this.mDirectoryList.clear();

        /* Add the "." == "current directory" */
		/*this.mDirectoryList.add(new IconText(getString(R.string.current_dir),
				getResources().getDrawable(R.drawable.folder32), 1));*/
        //返回上级
        if (!this.mCurrentDirectory.getParent().equals("/")) {
            this.mDirectoryList.add(new IconText("返回上级目录", getResources().getDrawable(R.drawable.ic_menu_revert), 0, "", false));
        }

        Drawable currentIcon = null;
        for (File currentFile : files) {
            String fileName = currentFile.getName();
            //排除.开头的文件和目录
            if (dotStart(fileName)) {
                continue;
            }

            String path = "";
            if (currentFile.isDirectory()) {
                currentIcon = getResources().getDrawable(R.drawable.folder32);
            }
//            else if (checkEnds(fileName, fileEnds)) {
//                currentIcon = getResources().getDrawable(R.drawable.category_icon_document);
//                path = currentFile.getAbsolutePath();
//            }
            else {
                continue;
            }

            switch (this.mDisplayMode) {
                case ABSOLUTE:
                    this.mDirectoryList.add(new IconText(currentFile.getPath(), currentIcon, currentFile.isDirectory() ? 0 : 1, path, currentFile.isDirectory()));
                    break;
                case RELATIVE:
                    //int currentPathStringLenght = this.mCurrentDirectory.getAbsolutePath().length();

                    this.mDirectoryList.add(new IconText(currentFile.getName(), currentIcon, currentFile.isDirectory() ? 0 : 1, path, currentFile.isDirectory()));
                    break;
            }
        }
        Collections.sort(this.mDirectoryList);

        //mDirectoryListAdapter = new IconTextListAdapter(this);
        mDirectoryListAdapter.setListItems(this.mDirectoryList);

        listFileBrowser.setAdapter(mDirectoryListAdapter);
    }


    /**
     * 是否是.点开头的文件或目录
     *
     * @param fileName
     * @return
     */
    private boolean dotStart(String fileName) {
        //Log.d(tag, "dotStart fileName=" + fileName);
        return fileName.startsWith(".");
    }


    /**
     * List单击选中事件,打开任务编辑
     *
     * @author wangpeng
     */
    public class ListOnItemClick implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

            try {
                int selectionRowID = (int) id;
                String selectedFileString = mDirectoryList.get(selectionRowID).getText();

                if (selectedFileString.equals("当前目录")) {
                    // Refresh
                    browseToWhere(mCurrentDirectory);
                } else if (selectedFileString.equals("返回上级目录")) {
                    upOneLevel();

                } else {
                    File file = null;
                    switch (mDisplayMode) {
                        case RELATIVE:
                            file = new File(mCurrentDirectory.getAbsolutePath() + "/" + mDirectoryList.get(selectionRowID).getText());
                            //openFile(file);

                            break;
                        case ABSOLUTE:
                            file = new File(mDirectoryList.get(selectionRowID).getText());
                            FILE_PATH = file.getAbsolutePath();
                            //openFile(file);
                            break;
                    }

                    if (file != null && file.isFile()) {
                        IconText it = mDirectoryList.get(selectionRowID);
                        it.setSelect(!it.isSelect());

                        mDirectoryListAdapter.notifyDataSetChanged();
                    }

                    if (file != null && file.isDirectory())
                        browseToWhere(file);
                }
            } catch (Exception ex) {
                Log.e(t, "ListOnItemClick error.", ex);
            }
        }
    }


    /**
     * Check the string ends
     *
     * @param checkItsEnd
     * @param fileEndings
     * @return
     */
    private boolean checkEnds(String checkItsEnd, String[] fileEndings) {
        for (String aEnd : fileEndings) {
            checkItsEnd = checkItsEnd.toLowerCase();
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    private OnItemLongClickListener onItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long id) {
            FILE_PATH = mCurrentDirectory.getAbsolutePath()
                    + mDirectoryList.get((int) id).getText();
            showDialog(DELETEFILE);
            return true;
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DELETEFILE:// 删除文件对话框
                return new AlertDialog.Builder(FolderBrowser.this).setTitle(
                        "删除文件").setMessage("确定要删除该文件么？").setPositiveButton(
                        "确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                setProgressBarIndeterminateVisibility(false);
                                File f = new File(FILE_PATH);
                                f.delete();
                                setProgressBarIndeterminateVisibility(true);
                                browseToWhere(mCurrentDirectory);

                            }
                        }).setNeutralButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();

            default:
                return null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.browseToWhere(this.mCurrentDirectory);
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.browseToWhere(this.mCurrentDirectory);
        }
        if (getResources().getConfiguration().keyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            // 打开键盘
            this.browseToWhere(this.mCurrentDirectory);
        }
        if (getResources().getConfiguration().keyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
            // 关闭键盘
            this.browseToWhere(this.mCurrentDirectory);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(t, "onCheckedChanged buttonView=" + buttonView.getId() + ", isChecked=" + isChecked);

        if (isChecked) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(t, "start onResume");
        setProgressBarIndeterminateVisibility(false);

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}