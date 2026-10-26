import HealthIndicator from './HealthIndicator';
import ProductGrid from './ProductGrid';

export default function DashboardView() {
    return (
        <div style={{ padding: '2rem', fontFamily: 'sans-serif' }}>
            <h1>Dashboard Katalog Produk Pariwisata</h1>
            <HealthIndicator />
            <hr style={{ margin: '2rem 0' }} />
            <h2>Daftar Produk</h2>
            <ProductGrid />
        </div>
    );
}