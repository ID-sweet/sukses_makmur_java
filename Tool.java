	public ArrayList getStok = new ArrayList();
 
	public void loadAllStok(String kode_barang) {
        sql = "SELECT stok_satcil FROM gudang_barang WHERE kode_barang = '" + kode_barang + "'";
        try {
            PreparedStatement insert = frmLogin.kon.connection.prepareStatement(sql);
            ResultSet set = insert.executeQuery();
            boolean cekSisa = false;
            if (set.next()) {
                sql = "SELECT kode_barang,satuan,urutan FROM gudang_barang_satuan WHERE kode_barang = '" + kode_barang + "' ORDER BY urutan";
                try {
                    PreparedStatement insert1 = frmLogin.kon.connection.prepareStatement(sql);
                    ResultSet set1 = insert1.executeQuery();
                    double stok = 0;
                    getStok.removeAll(getStok);
                    while (set1.next()) {
                        double nisi = 0;
                        int urutan = set1.getInt("urutan");
                        sql = "SELECT isi FROM gudang_barang_satuan WHERE kode_barang = '" + set1.getString("kode_barang") + "' AND urutan>=" + urutan;
                        try {
                            PreparedStatement insert2 = frmLogin.kon.connection.prepareStatement(sql);
                            ResultSet set2 = insert2.executeQuery();
                            while (set2.next()) {
                                if (nisi > 0) {
                                    nisi = nisi * set2.getDouble("isi");
                                } else {
                                    nisi = set2.getDouble("isi");
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("getStok " + ex);
                        }
                        //return isi terkecil  per satuan
                        //hitung stok per satuan
                        if (cekSisa == false) {
                            if (stok == 0) {
                                stok = set.getDouble("stok_satcil");
                            }
                        }
                        double nstok = stok / nisi;
                        double pembulatan_stok = Math.floor(nstok * 1e0) / 1e0;
                        double sisa = stok % nisi;
                        String satuan = set1.getString("satuan");
                        getStok.add(formater.format(pembulatan_stok) + "&" + satuan);
                        if (sisa == 0) {
                            cekSisa = true;
                        }
                        stok = sisa;
                    }
                } catch (Exception ex) {
                    System.out.println("getStok " + ex);
                }
            }
        } catch (Exception ex) {
            System.out.println("getStok " + ex);
        }
    }