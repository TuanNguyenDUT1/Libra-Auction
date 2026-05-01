import home_banner from '@/public/home_banner.jpg';
import Image from 'next/image';
import Link from 'next/link';
export default function HomeBanner() {
    return (
        <div className='relative w-full h-180'>
            <div className='absolute size-full'>
                <Image src={home_banner} alt="" className='size-full object-cover'></Image>
            </div>
            <div className='absolute size-full grid grid-cols-2'>
                <div className='col-start-2 px-8 py-18'>
                    <div className="w-full h-[76vh] flex flex-col items-start bg-(--background-color)/90 rounded-2xl px-9 py-12">
                        <p className="font-semibold tracking-widest">Live Auctions Now Open</p>
                        <h1 className="font-extrabold text-[clamp(2.5rem,var(--home-banner-title-font-size),6rem)] text-(--primary-color)">
                            Discover and Join Live Online Auctions
                        </h1>
                        <p className="text-(length:--home-banner-subtitle-font-size)">
                            Bid on thousands of premium products in real time. Transparent pricing,
                            secure transactions, and fair competition — all in one professional
                            auction platform.
                        </p>
                        <Link className="mt-auto bg-(--primary-color) text-white px-12 py-4 font-bold hover:bg-(--primary-color)/90 active:bg-(--primary-color)/80" href="/auctions">
                            Join the Auction
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}