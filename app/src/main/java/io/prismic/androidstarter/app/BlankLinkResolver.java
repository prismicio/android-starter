package io.prismic.androidstarter.app;

import io.prismic.DocumentLinkResolver;
import io.prismic.Fragment;

public class BlankLinkResolver extends DocumentLinkResolver {
  @Override
  public String resolve(Fragment.DocumentLink link) {
    return "";
  }
}
