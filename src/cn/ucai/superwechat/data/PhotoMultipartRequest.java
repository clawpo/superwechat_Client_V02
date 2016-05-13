package cn.ucai.superwechat.data;

public class PhotoMultipartRequest{//<T> extends Request<T> {
//
//    private static final String FILE_PART_NAME = "file";
//
//    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
//    private final Response.Listener<T> mListener;
//    private final File mImageFile;
//    protected Map<String, String> headers;
//
//    public PhotoMultipartRequest(String url,
//                                 ErrorListener errorListener,
//                                 Listener<T> listener,
//                                 File imageFile) {
//        super(Method.POST, url, errorListener);
//
//        mListener = listener;
//        mImageFile = imageFile;
//
//        buildMultipartEntity();
//    }
//
//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        Map<String, String> headers = super.getHeaders();
//
//        if (headers == null
//                || headers.equals(Collections.emptyMap())) {
//            headers = new HashMap<String, String>();
//        }
//
//        headers.put("Accept", "application/json");
//
//        return headers;
//    }
//
//    private void buildMultipartEntity()
//    {
//        mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile, ContentType.create("image/jpeg"), mImageFile.getName());
//        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
//    }
//
//    @Override
//    public String getBodyContentType()
//    {
//        String contentTypeHeader = mBuilder.build().getContentType().getValue();
//        return contentTypeHeader;
//    }
//
//    @Override
//    public byte[] getBody() throws AuthFailureError
//    {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        try
//        {
//            mBuilder.build().writeTo(bos);
//        }
//        catch (IOException e)
//        {
//            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
//        }
//
//        return bos.toByteArray();
//    }
//
//    @Override
//    protected Response<T> parseNetworkResponse(NetworkResponse response)
//    {
//        T result = null;
//        return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
//    }
//
//    @Override
//    protected void deliverResponse(T response)
//    {
//        mListener.onResponse(response);
//    }
}

