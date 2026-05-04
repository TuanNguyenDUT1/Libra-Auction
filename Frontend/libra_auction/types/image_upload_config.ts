export interface ImageUploadConfig {
    uploadUrl: string;
    publicId: string;
    apiKey: string;
    timestamp: number;
    signature: string;
    additionalParams: Record<string, object>;
}