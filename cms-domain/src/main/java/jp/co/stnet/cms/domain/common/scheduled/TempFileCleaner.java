/*
 * Copyright(c) 2013 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.co.stnet.cms.domain.common.scheduled;

import jp.co.stnet.cms.domain.service.common.FileManagedSharedService;
import jp.co.stnet.cms.domain.service.common.FileUploadSharedService;
import org.springframework.beans.factory.annotation.Value;
import org.terasoluna.gfw.common.date.ClassicDateFactory;

import javax.inject.Inject;

public class TempFileCleaner {

    @Inject
    ClassicDateFactory dateFactory;
    
    @Inject
    FileUploadSharedService fileUploadSharedService;

    @Inject
    FileManagedSharedService fileManagedSharedService;
    
    @Value("${security.tempFileCleanupSeconds}")
    int cleanupInterval;
    
    public void cleanup() {
        fileUploadSharedService.cleanUp(dateFactory.newTimestamp().toLocalDateTime().minusSeconds(cleanupInterval));
        fileManagedSharedService.cleanup(dateFactory.newTimestamp().toLocalDateTime().minusSeconds(cleanupInterval));
    }
}
