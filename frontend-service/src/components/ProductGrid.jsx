import { useState, useEffect } from 'react';
import axios from 'axios';

export default function ProductGrid() {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get('/api/products');
                setProducts(response.data);
            } catch (error) {
                console.error("Gagal mengambil data produk", error);
            }
        };
        fetchProducts();
    }, []);

    return (
    <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap' }}>
        {Array.isArray(products) && products.length > 0 ? (
            products.map(product => (
                <div key={product.id} style={{ border: '1px solid #ccc', padding: '1rem' }}>
                    <h3>{product.name}</h3>
                    <p>{product.description}</p>
                    <strong>Rp {product.price}</strong>
                </div>
            ))
        ) : (
            <p>Belum ada produk atau gagal memuat data server.</p>
        )}
    </div>
);
}