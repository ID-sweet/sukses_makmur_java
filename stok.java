	public void getAllStok(String getnama_barang) {
        Tool tl = new Tool();
        refreshStokSistem();
        sql = "SELECT kode_barang,nama_barang FROM " + tabelGudangBarang + " WHERE nama_barang ILIKE '%" + getnama_barang + "%' order by nama_barang";
        try {
            ResultSet set = frmLogin.kon.statement.executeQuery(sql);
            while (set.next()) {
                String[] kolom = {"Kode Barang", "Nama Barang", "Stok"};
                modelStokSistem.setColumnIdentifiers(kolom);
                try {

                    tl.loadAllStok(set.getString("kode_barang"));
                    for (int n = 0; n < tl.getStok.size(); n++) {
                        String[] nstok = tl.getStok.get(n).toString().split("&");
                        if (Double.parseDouble(nstok[0]) > 0) {
                            boolean kodeSama = false;
                            int row = FrmStokStokAll.tabelStokSistem.getRowCount();
                            for (int nrow = 0; nrow < row; nrow++) {
                                String dicek = set.getString("kode_barang");
                                String cekMasuk = FrmStokStokAll.tabelStokSistem.getValueAt(nrow, 0).toString();
                                if (dicek.equalsIgnoreCase(cekMasuk)) {
                                    kodeSama = true;
                                    break;
                                }
                            }
                            String kode_barang = "";
                            String nama_barang = "";
                            if (kodeSama == false) {
                                kode_barang = set.getString("kode_barang");
                                nama_barang = set.getString("nama_barang");
                            }
                            String[] data = {kode_barang, nama_barang, nstok[0] + " " + nstok[1]};
                            modelStokSistem.addRow(data);
                        }
                    }

                } catch (Exception ex) {
                    System.out.println("tampilData barang " + ex);
                }
            }
        } catch (Exception ex) {
            System.out.println("getAllKodeBarang " + ex);
        }
    }