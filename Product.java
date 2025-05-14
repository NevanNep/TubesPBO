public class Mahasiswa {
    private String nama;
    private Prodi prodi;

    public Mahasiswa(String nama, Prodi prodi) {
        this.nama = nama;
        this.prodi = prodi;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setProdi(Prodi prodi) {
        this.prodi = prodi;
    }

    public String getNama() {
        return nama;
    }

    public String getProdi() {
        return prodi.getNama();
    }

    public void displayMahasiswa() {
        System.out.println("Nama: " + nama);
        System.out.println("Prodi: " + prodi.getNama());
    }
}
