import Link from "next/link";
import BannerImage from "@/public/background_login.jpg";
import Image from "next/image";
import GoogleSignInButton from "@/components/main/auth/sign_in_with_google";
import SignInWithPassword from "@/components/main/auth/sign_in_with_password";
export default function page() {
  return (
    <div className="p-8 grid grid-cols-2 gap-6">
      <div className="bg-(--background-color)/60 h-(40hv) rounded-3xl px-24 py-12">
        <div className="flex flex-col gap-5">
          <p className="text-lg">Welcome to LibraAuction</p>
          <p className="text-3xl font-bold">
            A transparent, secure, and real-time online auction platform.
          </p>
          <p>
            Sign in to access live auctions, bid in real time, monitor your
            activity, and securely manage all aspects of your auction
            experience.
          </p>
        </div>
        <SignInWithPassword />
        <div className="flex px-3 py-2">
          <div className="flex-1">
            <div className="h-[50%] border-b border-black/60"></div>
          </div>
          <div className="px-4 text-sm font-semibold text-black/60">OR</div>
          <div className="flex-1">
            <div className="h-[50%] border-b border-black/60"></div>
          </div>
        </div>
        <GoogleSignInButton />
        <div className="mt-4">
          <Link
            href="#"
            className="hover:text-(--primary-color) active:text-(--secondary-color)"
          >
            Forgot your password?
          </Link>
        </div>
      </div>
      <div className="relative">
        <div className="absolute size-full overflow-hidden rounded-(--box-radius) [--box-radius:1.5rem]">
          <Image src={BannerImage} alt="" className="object-cover h-full" />
        </div>
        <div className="absolute size-full">
          <div className="flex flex-col size-full bg-(--accent-color)/20 rounded-(--box-radius) p-(--box-padding) [--box-radius:1.5rem] [--box-padding:0.5rem]">
            <div className="flex-1">
              <div className="p-8">
                <p className="font-extrabold text-5xl text-white/80">
                  Every bid tells a story of value and competition.
                </p>
              </div>
            </div>
            <div className="h-40 bg-gray-600/90 rounded-[calc(var(--box-radius)-var(--box-padding))] p-8">
              <Link
                href="/sign-up"
                className="px-6 py-2 border border-white text-white rounded-full cursor-pointer hover:bg-white/8 active:bg-white/16"
              >
                Don’t have an account? Sign up
              </Link>
              <p className="text-white mt-3">
                Sign up to participate in online auctions, place bids in real
                time, and manage your auction activities securely and
                efficiently.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
